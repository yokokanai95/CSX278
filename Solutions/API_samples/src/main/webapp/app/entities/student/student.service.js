(function() {
    'use strict';
    angular
        .module('apiSamplesApp')
        .factory('Student', Student);

    Student.$inject = ['$resource', 'DateUtils'];

    function Student ($resource, DateUtils) {
        var resourceUrl =  'api/students/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.graduationDate = DateUtils.convertLocalDateFromServer(data.graduationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.graduationDate = DateUtils.convertLocalDateToServer(data.graduationDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.graduationDate = DateUtils.convertLocalDateToServer(data.graduationDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
