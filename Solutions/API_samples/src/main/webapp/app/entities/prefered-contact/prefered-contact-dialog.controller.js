(function() {
    'use strict';

    angular
        .module('apiSamplesApp')
        .controller('PreferedContactDialogController', PreferedContactDialogController);

    PreferedContactDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PreferedContact', 'Student'];

    function PreferedContactDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PreferedContact, Student) {
        var vm = this;

        vm.preferedContact = entity;
        vm.clear = clear;
        vm.save = save;
        vm.students = Student.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.preferedContact.id !== null) {
                PreferedContact.update(vm.preferedContact, onSaveSuccess, onSaveError);
            } else {
                PreferedContact.save(vm.preferedContact, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('apiSamplesApp:preferedContactUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
