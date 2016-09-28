(function() {
    'use strict';

    angular
        .module('apiSamplesApp')
        .controller('PreferedContactDeleteController',PreferedContactDeleteController);

    PreferedContactDeleteController.$inject = ['$uibModalInstance', 'entity', 'PreferedContact'];

    function PreferedContactDeleteController($uibModalInstance, entity, PreferedContact) {
        var vm = this;

        vm.preferedContact = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PreferedContact.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
