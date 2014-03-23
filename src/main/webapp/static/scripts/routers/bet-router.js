superBetApp.config([ '$routeProvider', '$httpProvider',
		function($routeProvider, $httpProvider, Bookmakers) {
			$routeProvider.when('/bets/search', {
				templateUrl : 'views/betview.html',
				controller : 'BetController',
				resolve : {
					resolvedDtos : [ 'Bets', function(service) {
						return service.query();
					} ],
					metamodel : function() {
						return superbetMetamodel.bet
					}
				}
			})
		} ]);