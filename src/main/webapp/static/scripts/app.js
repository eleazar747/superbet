var superBetApp = angular.module('superBetApp', ['ngResource','ngRoute','ui.bootstrap','angularFileUpload']);
superBetApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/metrics', {
        templateUrl: 'views/metricsview.html',
        controller: 'MetricsController',
        resolve:{
            resolvedMetrics:['Metrics', function (Metrics) {
                return Metrics.get();
            }]
        }
    }).otherwise({
		redirectTo : '/static/html/index.html'
	});
} ]);