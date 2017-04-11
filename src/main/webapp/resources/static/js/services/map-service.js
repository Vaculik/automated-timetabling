
app.service('MapService', [
    'Point',
    'Vector',
    'ModelService',
    function(Point,
             Vector,
             ModelService) {
        'use strict';

        const mapId = 'leaflet-map';
        const initialCoords = [53.345, -6.269];
        const initialZoom = 13;
        const maxZoom = 18;
        const arrowHeadPointDist = 0.0001;
        const arrowLength = 0.0002;
        const arrowSidePointsRatio = 0.3;
        const latLonRate = 1.65;

        let service = this;
        let leafletMap;
        let arrowTailPointDist = arrowHeadPointDist + arrowLength;
        let leafletSites = [];
        let leafletRoutes = [];
        let leafletEdges = [];
        let leafletBuses = [];
        let edgesMap = new Map();

        let busExampleOnMap = null;

        service.showSites = function(sites, options) {
            let {withArms, withEdges} = options;
            for (let site of sites) {
                let siteLayer = paintSite(site);
                siteLayer.site = site;
                siteLayer.on('click', () => clickOnSite(siteLayer));
                leafletSites.push(siteLayer);
                if (withArms) {
                    siteLayer.arrowsOnMap = paintArms(site);
                }
            }
            if (withEdges) {
                service.showEdges();
            }
        };

        service.hideSites = function() {
            for (let site of leafletSites) {
                if (site.arrowsOnMap) {
                    removePaintedItems(site.arrowsOnMap);
                }
            }
            removePaintedItems(leafletSites);
            service.hideEdges();
        };

        service.showRoutes = function(routes) {
            for (let route of routes) {
                leafletRoutes.push(paintRoute(route));
            }
        };

        service.hideRoutes = function() {
            removePaintedItems(leafletRoutes);
        };

        service.showArms = function() {
            for (let siteLayer of leafletSites) {
                if (!siteLayer.arrowsOnMap) {
                    siteLayer.arrowsOnMap = paintArms(siteLayer.site);
                }
            }
        };

        service.hideArms = function() {
            for (let siteLayer of leafletSites) {
                if (siteLayer.arrowsOnMap) {
                    removePaintedItems(siteLayer.arrowsOnMap);
                    siteLayer.arrowsOnMap = null;
                }
            }
        };

        service.drawBus = function(bus) {
            service.hideBus();
            busExampleOnMap = paintBus(bus);
        };

        service.hideBus = function() {
            if (busExampleOnMap) {
                leafletMap.removeLayer(busExampleOnMap);
                busExampleOnMap = null;
            }
        };

        service.showEdges = function() {
            ModelService.getSitesMap().then((sitesMap) => {
                for (let siteLayer of leafletSites) {
                    paintEdges(siteLayer.site, sitesMap);
                }
            });
        };

        service.hideEdges = function() {
            removePaintedItems(leafletEdges);
            edgesMap.clear();
        };

        service.showBuses = function(buses) {
            for (let bus of buses) {
                leafletBuses.push(paintBus(bus));
            }
        };

        service.hideBuses = function() {
            removePaintedItems(leafletBuses);
        };

        service.init = function() {
            leafletMap = L.map(mapId).setView(initialCoords, initialZoom);
            L.tileLayer('https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}' +
                '?access_token=pk.eyJ1IjoidmFjdWxsaWsiLCJhIjoiY2owMm52MTZiMDNoYzJ3cW43N202eDh3MSJ9.ofm-d-mdcw7a9SqoxpGL9g', {
                attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
                'Imagery &copy; <a href="http://mapbox.com">Mapbox</a>',
                maxZoom: maxZoom,
                accessToken: 'pk.eyJ1IjoidmFjdWxsaWsiLCJhIjoiY2owMm52MTZiMDNoYzJ3cW43N202eDh3MSJ9.ofm-d-mdcw7a9SqoxpGL9g'
            }).addTo(leafletMap);
        };

        function paintBus(bus) {
            let arrayCoordinates = makeArrayCoords(bus.coordinates);
            let bounds = [arrayCoordinates, [arrayCoordinates[0] + 0.0002, arrayCoordinates[1] + 0.0001]];
            return L.rectangle(bounds, {
                color: 'blue',
                weight: 5,
            }).addTo(leafletMap);
        }

        function clickOnSite(siteLayer) {
            if (siteLayer.arrowsOnMap) {
                removePaintedItems(siteLayer.arrowsOnMap);
                siteLayer.arrowsOnMap = null;
            } else {
                siteLayer.arrowsOnMap = paintArms(siteLayer.site);
            }
        }

        function paintSite(site) {
            return L.circle(makeArrayCoords(site.coordinates), {
                color: 'green',
                fillColor: '#00ff33',
                fillOpacity: 0.5,
                radius: 5
            }).addTo(leafletMap);
        }

        function paintRoute(route) {
            let arrayCoords = [];
            for (let coords of route.coordinates) {
                arrayCoords.push(makeArrayCoords(coords));
            }
            return L.polyline(arrayCoords, {
                color: 'red'
            }).addTo(leafletMap);
        }

        function paintArms(site) {
            let paintedArms = [];
            for (let arm of site.arms) {
                let armArrow = getArmLineCoords(site.coordinates, arm.armAngle);
                let paintedArrow = paintArmLine(armArrow);
                paintedArrow.bindPopup(arm.streetName);
                paintedArms.push(paintedArrow);
            }
            return paintedArms;
        }

        function paintEdges(site, sitesMap) {
            let relevantArms = site.arms.filter((arm) => arm.nextSiteId);
            for (let arm of relevantArms) {
                if (isEdgePainted(site.id, arm.nextSiteId) || !sitesMap.has(arm.nextSiteId)) {
                    continue;
                }
                let targetSite = sitesMap.get(arm.nextSiteId);
                let edgeCoords = getEdgeCoords(site, arm, targetSite);
                if (edgeCoords) {
                    leafletEdges.push(paintOneEdge(edgeCoords));
                    addEdgeToMap(site.id, targetSite.id);
                }
            }
        }

        function paintOneEdge(edgeCoords) {
            let coords = [];
            coords.push(edgeCoords.fromPoint.getArrayCoords());
            coords.push(edgeCoords.toPoint.getArrayCoords());
            return L.polyline(coords, {
                color: 'yellow',
                weight: 5,
            }).addTo(leafletMap);
        }

        function addEdgeToMap(key, targetSiteId) {
            if (edgesMap.has(key)) {
                edgesMap.get(key).push(targetSiteId);
            } else {
                edgesMap.set(key, [targetSiteId]);
            }
        }

        function getEdgeCoords(fromSite, arm, targetSite) {
            let appropriateArm = targetSite.arms.find((arm) => arm.nextSiteId === fromSite.id);
            let fromPoint = getArmPoint(fromSite, arm);
            if (appropriateArm) {
                return {
                    fromPoint: fromPoint,
                    toPoint: getArmPoint(targetSite, appropriateArm),
                };
            } else {
                console.log('Appropriate arm not found.');
                return {
                    fromPoint: fromPoint,
                    toPoint: new Point(targetSite.coordinates.latitude, targetSite.coordinates.longitude),
                };
            }
        }

        function getArmPoint(site, arm) {
            let sitePoint = new Point(site.coordinates.latitude, site.coordinates.longitude);
            let radianAngle = arm.armAngle * Math.PI / 180;
            return calculateArmHeadPoint(sitePoint, radianAngle);
        }

        function isEdgePainted(fromSiteId, toSiteId) {
            return isEdgeInMap(fromSiteId, toSiteId) || isEdgeInMap(toSiteId, fromSiteId);
        }

        function isEdgeInMap(keyId, targetId) {
            if (edgesMap.has(keyId)) {
                let targetIds = edgesMap.get(keyId);
                return targetIds.some((id) => id === targetId);
            }
            return false;
        }

        function paintArmLine(armCoords) {
            let coords = [];
            coords.push(armCoords.headPoint.getArrayCoords());
            coords.push(armCoords.tailPoint.getArrayCoords());
            return L.polyline(coords, {
                color: 'green',
                weight: 5,
            }).addTo(leafletMap);
        }

        function getArmLineCoords(coordinates, angle) {
            let radianAngle = angle * Math.PI / 180;
            let basePoint = new Point(coordinates.latitude, coordinates.longitude);
            let headPoint = calculateArmHeadPoint(basePoint, radianAngle);
            let tailPoint = calculateArmTailPoint(basePoint, radianAngle);
            return {
                headPoint: headPoint,
                tailPoint: tailPoint,
            };
        }

        function calculateArmHeadPoint(basePoint, radianAngle) {
            let y = -arrowHeadPointDist * Math.cos(radianAngle);
            let x = arrowHeadPointDist * Math.sin(radianAngle);
            let shiftPoint = new Point(x, y*latLonRate);
            return basePoint.add(shiftPoint);
        }

        function calculateArmTailPoint(basePoint, radianAngle) {
            let y = -arrowTailPointDist * Math.cos(radianAngle);
            let x = arrowTailPointDist * Math.sin(radianAngle);
            let shiftPoint = new Point(x, y*latLonRate);
            return basePoint.add(shiftPoint);
        }

        function calculateArrowSidePoints(headPoint, tailPoint) {
            let directionVector = new Vector(headPoint, tailPoint);
            let regulatedDirectionVector = directionVector.multiply(arrowSidePointsRatio);
            let normalVector = regulatedDirectionVector.normalVector();
            let adjustedNormalVectorPoint = new Point(normalVector.x/latLonRate, normalVector.y*latLonRate);
            let crossPoint = headPoint.subtract(regulatedDirectionVector);
            let sidePoint1 = crossPoint.add(adjustedNormalVectorPoint);
            let sidePoint2 = crossPoint.subtract(adjustedNormalVectorPoint);
            return [sidePoint1, sidePoint2];
        }

        function makeArrayCoords(coordinates) {
            return [coordinates.latitude, coordinates.longitude];
        }

        function removePaintedItems(items) {
            for (let item of items) {
                leafletMap.removeLayer(item);
            }
            items.splice(0, items.length);
        }
    }
]);