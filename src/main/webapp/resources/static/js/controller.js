app.controller('appController', [
    '$scope',
    'MapService',
    'ModelService',
    function($scope,
             MapService,
             ModelService) {
        'use strict';

        let webSocket;

        $scope.sitesVisible = false;
        $scope.routesVisible = false;
        $scope.armsVisible = false;
        $scope.busVisible = false;

        init();

        $scope.toggleSitesVisibility = function() {
            $scope.sitesVisible = !$scope.sitesVisible;
            if ($scope.sitesVisible) {
                ModelService.getAllSites().then((sites) => {
                    MapService.showSites(sites, {withArms: $scope.armsVisible});
                });
            } else {
                MapService.hideSites();
            }
        };

        $scope.toggleRoutesVisibility = function() {
            $scope.routesVisible = !$scope.routesVisible;
            if ($scope.routesVisible) {
                ModelService.getAllRoutes().then((routes) => {
                    MapService.showRoutes(routes);
                })
            } else {
                MapService.hideRoutes();
            }
        };

        $scope.toggleArmsVisibility = function() {
            $scope.armsVisible = !$scope.armsVisible;
            if ($scope.armsVisible) {
                MapService.showArms();
            } else {
                MapService.hideArms();
            }
        };

        $scope.toggleBusVisibility = function() {
            $scope.busVisible = !$scope.busVisible;
            if (!$scope.busVisible) {
                MapService.hideBus();
            }
        };

        function init() {
            MapService.init();

            webSocket = new SockJS('/websocket');
            let client = Stomp.over(webSocket);
            client.connect({}, function(frame) {
                client.subscribe('/server/alive', function(message) {
                });

                client.subscribe('/server/refresh', function(message) {
                    if ($scope.busVisible) {
                        ModelService.drawBusExample();
                    }
                });

                client.send('/channel/hello', {}, 'msg');
            });
        }
    }
]);