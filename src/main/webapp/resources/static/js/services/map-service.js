
app.service('MapService', [
    'Point',
    'Vector',
    function(Point,
             Vector) {
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
        let sitesOnMap = [];
        let routesOnMap = [];

        let busExampleOnMap = null;

        service.showSites = function(sites, options) {
            let {withArms} = options;
            for (let site of sites) {
                let siteLayer = paintSite(site);
                siteLayer.site = site;
                siteLayer.on('click', () => clickOnSite(siteLayer));
                sitesOnMap.push(siteLayer);
                if (withArms) {
                    siteLayer.arrowsOnMap = paintArms(site);
                }
            }
        };

        service.hideSites = function() {
            for (let site of sitesOnMap) {
                if (site.arrowsOnMap) {
                    removePaintedItems(site.arrowsOnMap);
                }
            }
            removePaintedItems(sitesOnMap);
        };

        service.showRoutes = function(routes) {
            for (let route of routes) {
                routesOnMap.push(paintRoute(route));
            }
        };

        service.hideRoutes = function() {
            removePaintedItems(routesOnMap);
        };

        service.showArms = function() {
            for (let siteLayer of sitesOnMap) {
                if (!siteLayer.arrowsOnMap) {
                    siteLayer.arrowsOnMap = paintArms(siteLayer.site);
                }
            }
        };

        service.hideArms = function() {
            for (let siteLayer of sitesOnMap) {
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
            let arrowsOnMap = [];
            for (let arm of site.arms) {
                let armArrow = makeArmArrow(site.coordinates, arm.armAngle);
                arrowsOnMap.push(paintArmArrow(armArrow));
            }
            return arrowsOnMap;
        }

        function paintArmArrow(arrow) {
            let coords = [];
            coords.push(arrow.sidePoints[0].getArrayCoords());
            coords.push(arrow.headPoint.getArrayCoords());
            coords.push(arrow.tailPoint.getArrayCoords());
            coords.push(arrow.headPoint.getArrayCoords());
            coords.push(arrow.sidePoints[1].getArrayCoords());
            return L.polyline(coords, {
                color: 'green'
            }).addTo(leafletMap);
        }

        function makeArmArrow(coordinates, angle) {
            let radianAngle = angle * Math.PI / 180;
            let basePoint = new Point(coordinates.latitude, coordinates.longitude);
            let headPoint = calculateArrowHeadPoint(basePoint, radianAngle);
            let tailPoint = calculateArrowTailPoint(basePoint, radianAngle);
            return {
                headPoint: headPoint,
                tailPoint: tailPoint,
                sidePoints: calculateArrowSidePoints(headPoint, tailPoint),
            };
        }

        function calculateArrowHeadPoint(basePoint, radianAngle) {
            let y = arrowHeadPointDist * Math.cos(radianAngle);
            let x = arrowHeadPointDist * Math.sin(radianAngle);
            let shiftPoint = new Point(x, y*latLonRate);
            return basePoint.add(shiftPoint);
        }

        function calculateArrowTailPoint(basePoint, radianAngle) {
            let y = arrowTailPointDist * Math.cos(radianAngle);
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