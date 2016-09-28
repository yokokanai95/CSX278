(function() {
    'use strict';

    angular
        .module('apiSamplesApp')
        .controller('StudentDetailController', StudentDetailController);

    StudentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Student', 'PreferedContact', 'School', 'Dormitory'];

    function StudentDetailController($scope, $rootScope, $stateParams, previousState, entity, Student, PreferedContact, School, Dormitory) {
        var vm = this;

        vm.student = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('apiSamplesApp:studentUpdate', function(event, result) {
            vm.student = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
