superBetApp.config([ '$routeProvider', '$httpProvider',
		function($routeProvider, $httpProvider, Bookmakers) {
			$routeProvider.when('/bets/sure', {
				templateUrl : 'views/surebetview.html',
				controller : 'SureBetController',
				resolve : {
					resolvedDtos : [ 'Bets', function(service) {
						return service.sure();
					} ],
					metamodel : function() {
						return superbetMetamodel.surebet
					}
				}
			})
		} ]);