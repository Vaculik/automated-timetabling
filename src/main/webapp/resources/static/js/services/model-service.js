
app.service('ModelService', [
    'ModelRequestService',
    'MapService',
    function(ModelRequestService,
             MapService) {
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
            ModelRequestService.getBusExample().then((response) => {
                MapService.drawBus(response.data);
            });
        };
    }
]);