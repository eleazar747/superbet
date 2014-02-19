superBetApp.factory('Metrics', ['$resource',
    function ($resource) {
        return $resource('/metrics/metrics', {}, {
            'get': { method: 'GET'}
        });
    }]);

superBetApp.factory('HealthCheckService', ['$rootScope', '$http',
    function ($rootScope, $http) {
        return {
            check: function() {
                $http.get('/metrics/healthcheck')
                    .success(function(data, status, headers, config) {
                        $rootScope.healthCheck = data;
                    })
                    .error(function(data, status, headers, config) {
                        $rootScope.healthCheck = data;
                    })
            }
        };
    }]);