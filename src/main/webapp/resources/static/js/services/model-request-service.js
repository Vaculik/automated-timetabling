
app.service('ModelRequestService', [
    '$http',
    function($http) {

        let appUrl = '/api/model';
        let service = this;

        service.getAllSites = function() {
            let relativeUrl = '/sites';
            return processRequest('GET', relativeUrl);
        };

        service.getAllRoutes = function() {
            let relativeUrl = '/routes';
            return processRequest('GET', relativeUrl);
        };

        service.getBusExample = function() {
            let relativeUrl = '/bus-example';
            return processRequest('GET', relativeUrl);
        };


        /**
         * Send an asynchronous HTTP request to the API.
         *
         * @param {string} method
         *        HTTP request method ('GET', 'POST', 'PUT' or 'DELETE').
         * @param {string} relativeUrl
         *        Relative URL of the API entry point.
         * @param {?Object} data
         *        Data to submit in the request body.
         * @param {?Object} params
         *       Appended as GET parameters.
         * @param {?Object} headers
         *        Additional HTTP headers to set in the request.
         * @return {HttpPromise}
         */
        function processRequest(method, relativeUrl, data, params, headers) {
            data = data || {};
            params = params || {};

            let httpConf = {
                method: method,
                url: appUrl + relativeUrl,
                headers: {
                    'Content-Type': 'application/json'
                },
                params: params,
                data: data
            };

            if (headers) {
                angular.forEach(headers, function (val, key) {
                    httpConf.headers[key] = val;
                });
            }

            return $http(httpConf);
        }
    }
]);