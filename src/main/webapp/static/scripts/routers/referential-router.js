superBetApp.config([ '$routeProvider', '$httpProvider',
		function($routeProvider, $httpProvider,Bookmakers) {
			$routeProvider.when('/bookmakers', {
				templateUrl : 'views/refview.html',
				controller : 'BookmakersController',
				resolve : {
					resolvedDtos : [ 'Bookmakers', function(service) {
						return service.query();
					} ],
					metamodel : function() {return superbetMetamodel.bookmaker}
				}
			}).when('/sports', {
				templateUrl : 'views/refview.html',
				controller : 'SportsController',
				resolve : {
					resolvedDtos : [ 'Sports', function(service) {
						return service.query();
					} ],
					metamodel : function() {return superbetMetamodel.sport}
				}
			}).when('/bettypes', {
				templateUrl : 'views/refview.html',
				controller : 'BetTypesController',
				resolve : {
					resolvedDtos : [ 'BetTypes', function(service) {
						return service.query();
					} ],
					metamodel : function() {return superbetMetamodel.bettype}
				}
			}).when('/datamappings', {
				templateUrl : 'views/refview.html',
				controller : 'DataMappingsController',
				resolve : {
					resolvedDtos : [ 'DataMappings', function(service) {
						return service.query();
					} ],
					metamodel : function() {return superbetMetamodel.datamapping}
				}
			})
		} ]);