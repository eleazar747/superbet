superBetApp.factory('Bets', [ '$resource', function($resource) {
	return $resource('/rest/bets/:id', {}, {
		'query' : {
			method : 'GET',
			isArray : true
		},
		'get' : {
			method : 'GET'
		}
	});
} ]);
