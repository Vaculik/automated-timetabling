app.controller('appController', [
    '$scope',
    'MapService',
    'ModelService',
    function($scope,
             MapService,
             ModelService) {
        'use strict';

        $scope.sitesVisible = false;

        init();

        $scope.headingTitle = 'hello';

        $scope.toggleSitesVisibility = function() {
            $scope.sitesVisible = !$scope.sitesVisible;
            if ($scope.sitesVisible) {
                ModelService.getAllSites().then((sites) => {
                    MapService.showSites(sites);
                });
            } else {
                MapService.hideSites();
            }
        };

        function init() {
            MapService.init();
        }
    }
]);