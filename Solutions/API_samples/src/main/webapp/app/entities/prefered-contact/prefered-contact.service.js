(function() {
    'use strict';
    angular
        .module('apiSamplesApp')
        .factory('PreferedContact', PreferedContact);

    PreferedContact.$inject = ['$resource'];

    function PreferedContact ($resource) {
        var resourceUrl =  'api/prefered-contacts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
