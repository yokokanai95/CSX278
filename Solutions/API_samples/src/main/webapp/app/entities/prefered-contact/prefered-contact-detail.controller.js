(function() {
    'use strict';

    angular
        .module('apiSamplesApp')
        .controller('PreferedContactDetailController', PreferedContactDetailController);

    PreferedContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PreferedContact', 'Student'];

    function PreferedContactDetailController($scope, $rootScope, $stateParams, previousState, entity, PreferedContact, Student) {
        var vm = this;

        vm.preferedContact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('apiSamplesApp:preferedContactUpdate', function(event, result) {
            vm.preferedContact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
