
app.service('ModelService', [
    'ModelRequestService',
    function(ModelRequestService) {
        'use strict';

        let service = this;
        let sites = null;

        service.getAllSites = function() {
            if (sites) {
                return new Promise((resolve, reject) => resolve(sites));
            } else {
                return ModelRequestService.getAllSites().then((response) => {
                    sites = response.data;
                    return response.data;
                });
            }
        }
    }
]);