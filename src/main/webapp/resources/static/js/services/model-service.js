
app.service('ModelService', [
    'ModelRequestService',
    function(ModelRequestService) {
        'use strict';

        let service = this;
        let sites = null;
        let routes = null;
        let links = null;

        service.getAllSites = function() {
            if (sites) {
                return sites;
            } else {
                return ModelRequestService.getAllSites().then((response) => {
                    sites = response.data;
                    return response.data;
                });
            }
        };

        service.getSitesMap = function() {
            return Promise.resolve(service.getAllSites()).then((sites) => {
                let sitesMap = new Map();
                for (let site of sites) {
                    sitesMap.set(site.id, site);
                }
                return sitesMap;
            });
        };

        service.getAllRoutes = function() {
            if (routes) {
                return routes;
            } else {
                return ModelRequestService.getAllRoutes().then((response) => {
                    routes = response.data;
                    return response.data;
                });
            }
        };

        service.drawBusExample = function() {
            // ModelRequestService.getBusExample().then((response) => {
            //     MapService.drawBus(response.data);
            // });
        };

        service.getAllBuses = function() {
            return ModelRequestService.getAllBuses().then((response) => {
                return response.data;
            });
        };

        service.getAllLinks = function() {
            if (links) {
                return links;
            } else {
                return ModelRequestService.getAllLinks().then((response) => {
                    links = response.data;
                    return response.data;
                });
            }
        }
    }
]);