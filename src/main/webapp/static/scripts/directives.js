module.directive('file', function(){
    return {
        scope: {
            file: '='
        },
        link: function(scope, el, attrs){
            el.bind('change', function(event){
            	alert('zob');
                var files = event.target.files;
                var file = files[0];
                scope.file = file;
                scope.$apply();
            });
        }
    };
});