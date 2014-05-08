superBetApp.factory('Bets', [ '$resource', function($resource) {
	return $resource('/rest/bets/:action', {}, {
		'query' : {
			method : 'GET',
			isArray : true
		},
		'sure' : {
			params : {action : 'sures'},
			method : 'GET',
			isArray : true
		}
	});
} ]);
