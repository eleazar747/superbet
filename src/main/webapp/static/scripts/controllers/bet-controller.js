superBetApp.controller('BetController', [ '$scope', 'resolvedDtos', 'Bets',
		'metamodel', function($scope, resolvedDtos, Bets, metamodel) {
			$scope.dtos = resolvedDtos;
			$scope.metamodel = metamodel;

			$scope.refresh = function() {
				$scope.dtos = service.query();
			};
		} ]);