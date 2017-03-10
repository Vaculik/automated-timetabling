<!DOCTYPE html>
<!--[if lt IE 7]>      <html lang="en" ng-app="app" class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html lang="en" ng-app="app" class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html lang="en" ng-app="app" class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en" ng-app="app" class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Spring and Angularjs Tutorial</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../resources/static/css/app.css">
</head>

<body ng-app="app">
    <div ng-view class="view">
    </div>

    <div class="footer">
        <strong>Copyright &copy; 2015 <a href="http://www.muni.cz">Masaryk University</a></strong>
    </div>

    <script src="./webjars/angularjs/1.4.8/angular.js"></script>
    <script src="./webjars/angularjs/1.4.8/angular-resource.js"></script>
    <script src="./webjars/angularjs/1.4.8/angular-route.js"></script>
    <script src="../resources/static/js/app.js"></script>
    <script src="../resources/static/js/controller.js"></script>
    <script src="../resources/static/js/services/map-service.js"></script>
    <script src="../resources/static/js/services/model-request-service.js"></script>
    <script src="../resources/static/js/services/model-service.js"></script>
    <script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>

    <link rel="stylesheet" href="./webjars/bootstrap/3.3.6/css/bootstrap.css">
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
</body>
</html>
