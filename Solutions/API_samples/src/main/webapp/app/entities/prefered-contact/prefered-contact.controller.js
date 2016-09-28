(function() {
    'use strict';

    angular
        .module('apiSamplesApp')
        .controller('PreferedContactController', PreferedContactController);

    PreferedContactController.$inject = ['$scope', '$state', 'PreferedContact'];

    function PreferedContactController ($scope, $state, PreferedContact) {
        var vm = this;
        
        vm.preferedContacts = [];

        loadAll();

        function loadAll() {
            PreferedContact.query(function(result) {
                vm.preferedContacts = result;
            });
        }
    }
})();
