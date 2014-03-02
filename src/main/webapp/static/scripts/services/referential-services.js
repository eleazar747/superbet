function register(app, name, resource) {
	app.factory(name, [ '$resource', function($resource) {
		return $resource('/rest/' + resource + '/:id', {}, {
			'query' : {
				method : 'GET',
				isArray : true
			},
			'get' : {
				method : 'GET'
			}
		});
	} ]);
}
superBetApp.service('FileUpload', [ '$http', function($http) {
	this.uploadFileToUrl = function(file, uploadUrl) {
		var fd = new FormData();
		alert(file);
		fd.append('content', file);
		$http.post(uploadUrl, fd, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : '*/*'
			}
		}).success(function() {
			//alert('succes');
		}).error(function() {
			//alert('error');
		});
	}
} ]);
register(superBetApp, 'Bookmakers', 'bookmakers');
register(superBetApp, 'Sports', 'sports');
register(superBetApp, 'BetTypes', 'bettypes');
register(superBetApp, 'DataMappings', 'datamappings');
register(superBetApp, 'Matches', 'matches');
