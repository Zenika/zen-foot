(function () {
    'use strict';

    angular.module('zenFoot.app', [
        'zenContact.filters', 'zenFoot.directives',
        'ngCookies', 'ngResource', 'ngRoute',
        'ui.router', 'ui.bootstrap', 'ui.unique',
        'autofill-directive', 'ngGrid', 'angular-md5'
    ])
        .config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
            $stateProvider
                .state('loginState', {
                    url: '/login?subscriptionSuccess',
                    templateUrl: 'view/login.html'
                })
                .state('betsState', {
                    url: '/bets?gamblerId',
                    templateUrl: 'view/bets.html'
                })
                .state('adminState', {
                    url: '/admin',
                    templateUrl: 'view/admin-tmp.html'
                })
                .state('adminFinales', {
                    url: '/adminfinales',
                    templateUrl: 'view/admin-new-matchs.html'
                })
                .state('classState', {
                    url: '/classement',
                    templateUrl: 'view/classement.html'
                })
                .state('subscribeState', {
                    url: '/subscribe',
                    templateUrl: 'view/subscribe.html'
                })
                .state('confirmSubscription', {
                    url: '/confirmSubscription?email&token',
                    templateUrl: 'view/confirmSubscription.html'
                })
                .state('profileState', {
                    url: '/profil',
                    templateUrl: 'view/profil.html'
                })
                .state('resetPWD', {
                    url: '/reset_password/:resetid',
                    templateUrl: 'view/reset-password.html'
                })
                .state('ligueState', {
                    url: '/ligue',
                    templateUrl: 'view/ligues.html'
                })
                .state('ligueDetails', {
                    url: '/ligueDetails/:id',
                    templateUrl: 'view/ligue-details.html'
                })
                .state('events', {
                    url: '/events',
                    templateUrl: 'view/admin-events.html'
                })
                .state('users', {
                    url: '/users',
                    templateUrl: 'view/admin-users.html'
                });

            $urlRouterProvider.otherwise('/bets');

            $httpProvider.interceptors.push(function ($q, $location, $injector) {

                var getAuthService = function () {
                    return $injector.get('authService');
                };
                var getState = function () {
                    return $injector.get('$state');
                };

                return {
                    responseError: function (rejection) {
                        if (rejection.status === 401 && !isUnconnectedState(getState().current.name)) {
                            getAuthService().logout();
                        } else if (rejection.status === 403) {
                            getAuthService().redirectToHome();
                        }
                        return $q.reject(rejection);
                    }
                }
            })
        })
        .run(function ($rootScope, $state, $log, Session, authService) {
            $rootScope.$on('$stateChangeStart', function (evt, toState, toParams, fromState, fromParams) {
                $log.log('stateChangeStart:' + toState.name);
                if (isUnconnectedState(toState.name)) {
                    return;
                }

                if (authService.isConnected()) {
                    Session.get().$promise.then(function (data) {
                        checkAccessAuthorization(evt, toState, data);
                    });
                } else {
                    evt.preventDefault();
                    $state.go('loginState');
                }
            });

            function checkAccessAuthorization(evt, toState, data) {
                var principal = data.principal;
                if (toState.name === 'loginState') {
                    evt.preventDefault();
                    $state.go('betsState');
                } else if (adminAuthorized(toState.name) && !authService.isAdmin(principal)) {
                    evt.preventDefault();
                    $state.go('betsState');
                } else if (!adminAuthorized(toState.name) && authService.isAdmin(principal)) {
                    evt.preventDefault();
                    $state.go('adminState');
                }
            };

            // Makes the navbar collapse when a link is clicked
            // See https://github.com/twbs/bootstrap/issues/9013
            // Comment from danielward on Aug 8, 2013
            $('.navbar a').click(function () {
                var navbar_toggle = $('.navbar-toggle');
                if (navbar_toggle.is(':visible')) {
                    navbar_toggle.trigger('click');
                }
            });


            $rootScope.size = function (obj) {
                return _.size(obj);
            };
        });

        function isUnconnectedState(state){
                return _.contains(['loginState', 'resetPWD', 'subscribeState', 'confirmSubscription'], state);
        };

        function adminAuthorized(routeName) {
            var adminRoute = 'adminState';
            var finalesState = 'adminFinales';
            var adminUsers = 'users';
            var events = 'events';
            var adminRoutes = [adminRoute, finalesState, events, adminUsers];
            return _.contains(adminRoutes, routeName);
        };


}());
