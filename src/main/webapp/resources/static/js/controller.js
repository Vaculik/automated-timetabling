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
        $scope.busesVisible = false;
        $scope.edgesVisible = false;
        $scope.linksVisible = false;

        init();

        $scope.toggleSitesVisibility = function() {
            $scope.sitesVisible = !$scope.sitesVisible;
            if ($scope.sitesVisible) {
                Promise.resolve(ModelService.getAllSites()).then((sites) => {
                    let options = {
                        withArms: $scope.armsVisible,
                        withEdges: $scope.edgesVisible,
                        withLinks: $scope.linksVisible,
                    };
                    MapService.showSites(sites, options);
                });
            } else {
                MapService.hideSites();
            }
        };

        $scope.toggleRoutesVisibility = function() {
            $scope.routesVisible = !$scope.routesVisible;
            if ($scope.routesVisible) {
                Promise.resolve(ModelService.getAllRoutes()).then((routes) => {
                    MapService.showRoutes(routes);
                });
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

        $scope.toggleEdgesVisibility = function() {
            $scope.edgesVisible = !$scope.edgesVisible;
            if ($scope.edgesVisible) {
                MapService.showEdges();
            } else {
                MapService.hideEdges();
            }
        };

        $scope.toggleLinksVisibility = function() {
            $scope.linksVisible = !$scope.linksVisible;
            if ($scope.linksVisible) {
                if ($scope.sitesVisible) {
                    Promise.resolve(ModelService.getAllLinks()).then((links) => {
                        MapService.showLinks(links);
                    });
                }
            } else {
                MapService.hideLinks();
            }
        };

        $scope.toggleBusVisibility = function() {
            $scope.busVisible = !$scope.busVisible;
            if (!$scope.busVisible) {
                MapService.hideBus();
            }
        };

        $scope.toggleBusesVisibility = function() {
            $scope.busesVisible = !$scope.busesVisible;
            if ($scope.busesVisible) {
                redrawBuses();
            } else {
                MapService.hideBuses();
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
                    if ($scope.busesVisible) {
                        redrawBuses();
                    }
                });

                client.send('/channel/hello', {}, 'msg');
            });
        }

        function redrawBuses() {
            ModelService.getAllBuses().then((buses) => {
                MapService.hideBuses();
                MapService.showBuses(buses);
            });
        }
    }
]);