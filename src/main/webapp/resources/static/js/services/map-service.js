
angular.module('app').service('MapService', [
    function() {
        'use strict';

        const mapId = 'leaflet-map';
        const initialCoords = [53.345, -6.269];
        const initialZoom = 13;
        const maxZoom = 18;

        let service = this;
        let leafletMap;

        let sitesOnMap = [];

        service.showSites = function(sites) {
            for (let site of sites) {
                sitesOnMap.push(paintSite(site));
            }
        };

        service.hideSites = function() {
            for (let siteOnMap of sitesOnMap) {
                removePaintedItem(siteOnMap);
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

        function paintSite(site) {
            return L.circle([site.coordinates.latitude, site.coordinates.longitude], {
                color: 'green',
                fillColor: '#00ff33',
                fillOpacity: 0.5,
                radius: 5
            }).addTo(leafletMap);
        }

        function removePaintedItem(item) {
            leafletMap.removeLayer(item);
        }
    }
]);