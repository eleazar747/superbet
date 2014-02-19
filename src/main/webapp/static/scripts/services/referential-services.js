superBetApp.factory('Bookmakers', ['$resource',
    function ($resource) {
        return $resource('/rest/bookmakers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    }]);
superBetApp.factory('Sports', ['$resource',
                                   function ($resource) {
	return $resource('/rest/sports/:id', {}, {
		'query': { method: 'GET', isArray: true},
		'get': { method: 'GET'}
	});
}]);
superBetApp.factory('BetTypes', ['$resource',
                                   function ($resource) {
	return $resource('/rest/bettypes/:id', {}, {
		'query': { method: 'GET', isArray: true},
		'get': { method: 'GET'}
	});
}]);
superBetApp.factory('DataMappings', ['$resource',
                                   function ($resource) {
	return $resource('/rest/datamappings/:id', {}, {
		'query': { method: 'GET', isArray: true},
		'get': { method: 'GET'}
	});
}]);