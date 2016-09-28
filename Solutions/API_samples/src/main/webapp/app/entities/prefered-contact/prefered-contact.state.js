(function() {
    'use strict';

    angular
        .module('apiSamplesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('prefered-contact', {
            parent: 'entity',
            url: '/prefered-contact',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PreferedContacts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prefered-contact/prefered-contacts.html',
                    controller: 'PreferedContactController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('prefered-contact-detail', {
            parent: 'entity',
            url: '/prefered-contact/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PreferedContact'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/prefered-contact/prefered-contact-detail.html',
                    controller: 'PreferedContactDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PreferedContact', function($stateParams, PreferedContact) {
                    return PreferedContact.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'prefered-contact',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('prefered-contact-detail.edit', {
            parent: 'prefered-contact-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefered-contact/prefered-contact-dialog.html',
                    controller: 'PreferedContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreferedContact', function(PreferedContact) {
                            return PreferedContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prefered-contact.new', {
            parent: 'prefered-contact',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefered-contact/prefered-contact-dialog.html',
                    controller: 'PreferedContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nameOfChoice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('prefered-contact', null, { reload: 'prefered-contact' });
                }, function() {
                    $state.go('prefered-contact');
                });
            }]
        })
        .state('prefered-contact.edit', {
            parent: 'prefered-contact',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefered-contact/prefered-contact-dialog.html',
                    controller: 'PreferedContactDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreferedContact', function(PreferedContact) {
                            return PreferedContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prefered-contact', null, { reload: 'prefered-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('prefered-contact.delete', {
            parent: 'prefered-contact',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/prefered-contact/prefered-contact-delete-dialog.html',
                    controller: 'PreferedContactDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PreferedContact', function(PreferedContact) {
                            return PreferedContact.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('prefered-contact', null, { reload: 'prefered-contact' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
