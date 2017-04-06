
app.service('ModelService', [
    'ModelRequestService',
    function(ModelRequestService) {
        'use strict';

        let service = this;
        let sites = null;
        let routes = null;

        service.getAllSites = function() {
            if (sites) {
                return new Promise((resolve, reject) => resolve(sites));
            } else {
                return ModelRequestService.getAllSites().then((response) => {
                    sites = response.data;
                    return response.data;
                });
            }
        };

        service.getSitesMap = function() {
            return service.getAllSites().then((sites) => {
                let sitesMap = new Map();
                for (let site of sites) {
                    sitesMap.set(site.id, site);
                }
                return sitesMap;
            });
        };

        service.getAllRoutes = function() {
            if (routes) {
                return new Promise((resolve, reject) => resolve(routes));
            } else {
                return ModelRequestService.getAllRoutes().then((response) => {
                    routes = response.data;
                    return response.data;
                })
            }
        };

        service.drawBusExample = function() {
            // ModelRequestService.getBusExample().then((response) => {
            //     MapService.drawBus(response.data);
            // });
        };
    }
]);