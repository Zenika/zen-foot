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
                    url: '/bets',
                    templateUrl: 'view/bets.html'
                })
                .state('adminState', {
                    url: '/admin',
                    templateUrl: 'view/admin-tmp.html'
                })
                .state('adminFinales', {
                    url:'/adminfinales',
                    templateUrl:'view/admin-new-matchs.html'
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
                });
            /*  .state('ligueState', {
             url: '/ligue',
             templateUrl: 'view/profil.html'
             });*/

            $urlRouterProvider.otherwise('/bets');

            $httpProvider.interceptors.push(function ($q, $location, $injector) {

                var getAuthService = function() {
                    return $injector.get('authService');
                };
                var getState = function() {
                    return $injector.get('$state');
                };

                var isLoginState = function() {
                    return getState().current.name === 'loginState';
                };

                return {
                    responseError: function (rejection) {
                        if (rejection.status === 401 && !isLoginState()) {
                            getAuthService().logout();
                        } else if (rejection.status === 403) {
                            getAuthService().redirectToHome();
                        }
                        return $q.reject(rejection);
                    }
                }
            })
        })

        .run(function ($rootScope, $state) {
            var adminRoute = 'adminState';
            var finalesState='adminFinales';
            var loginRoute = 'loginState';
            var subscribeState = "subscribeState";
            var confirmSubscription = "confirmSubscription";
            var betsState = "betsState";
            var adminRoutes = [adminRoute,finalesState]

            var adminAuthorized = function(routeName){
                return _.contains(adminRoutes,routeName);
            }

            $rootScope.$on('$stateChangeSuccess', function (evt, toState, toParams, fromState, fromParams) {

                if (toState.name == subscribeState || toState.name == confirmSubscription) {
                    return;
                }

                if ($rootScope.isConnected()) {
                    if (toState.name === loginRoute) {
                        evt.preventDefault()
                        $state.go(betsState)
                    } else if (adminAuthorized(toState.name) && !$rootScope.isAdmin()) {
                        evt.preventDefault()
                        $state.go(betsState)
                    } else if (!adminAuthorized(toState.name) && $rootScope.isAdmin()) {
                        evt.preventDefault()
                        $state.go(adminRoute)
                    }
                } else {
                    if (toState.name !== loginRoute) {
                        evt.preventDefault()
                        $state.go(loginRoute)
                    }
                }
            })

            // Makes the navbar collapse when a link is clicked
            // See https://github.com/twbs/bootstrap/issues/9013
            // Comment from danielward on Aug 8, 2013
            $('.navbar a').click(function () {
                var navbar_toggle = $('.navbar-toggle');
                if (navbar_toggle.is(':visible')) {
                    navbar_toggle.trigger('click');
                }
            })
        })

        .constant('SCORE_REGEXP', /^[0-9]{1,2}$|^$/);
}());
