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
                    url: '/login',
                    templateUrl: 'view/login.html'
                })
                .state('betsState', {
                    url: '/bets',
                    templateUrl: 'view/bets.html'
                })
                .state('adminState', {
                    url: '/admin',
                    templateUrl: 'view/admin-tmp.html'
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
                    url: '/confirmSubscription/:id',
                    templateUrl: 'view/confirmSubscription.html'
                })
                .state('ligueState', {
                    url: '/profil',
                    templateUrl: 'view/profil.html'
                });

            $urlRouterProvider.otherwise('/bets');

            $httpProvider.interceptors.push(function ($q, authService, $rootScope, $location) {
                return {
                    responseError: function (rejection) {
                        if (rejection.status === 403 && $rootScope.isAdmin()) {
                            $location.path('/admin')
                        } else if (rejection.status === 401 || rejection.status === 403) {
                            authService.redirectToLogin()
                        }
                        return $q.reject(rejection)
                    }
                }
            })
        })

        .run(function ($rootScope, $state) {
            var adminRoute = 'adminState';
            var loginRoute = 'loginState';
            var subscribeState = "subscribeState";
            var confirmSubscription = "confirmSubscription";

            $rootScope.$on('$stateChangeSuccess', function (evt, toState, toParams, fromState, fromParams) {
                if (toState.name == subscribeState || toState.name == confirmSubscription) {
                    return;
                }

                if ($rootScope.isConnected() && toState.name === loginRoute) {
                    evt.preventDefault()
                    $state.go(fromState.name)
                } else if (!$rootScope.isConnected() && toState.name !== loginRoute) {
                    evt.preventDefault()
                    $state.go(loginRoute)
                } else if ($rootScope.isConnected() && $rootScope.isAdmin() && toState.name !== adminRoute) {
                    evt.preventDefault()
                    $state.go(adminRoute)
                }
            })

            // Makes the navbar collapse when a link is clicked
            // See https://github.com/twbs/bootstrap/issues/9013
            // Comment from danielward on Aug 8, 2013
            $('.navbar a').click(function () {
                var navbar_toggle = $('.navbar-toggle')
                if (navbar_toggle.is(':visible')) {
                    navbar_toggle.trigger('click')
                }
            })
        })

        .constant('SCORE_REGEXP', /^[0-9]{1,2}$|^$/)
}());
