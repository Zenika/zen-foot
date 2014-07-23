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
                .state('unconnected', {
                    abstract:true,
                    templateUrl: 'view/login-template.html'
                })
                 .state('unconnected.login', {
                    url: '/login?subscriptionSuccess',
                    templateUrl: 'view/login.html'
                })
                .state('index', {
                    abstract:true,
                    templateUrl: 'view/index-template.html'
                })
                .state('index.betsState', {
                    url: '/bets?gamblerId',
                    templateUrl: 'view/bets.html'
                })
                .state('index.adminState', {
                    url: '/admin',
                    templateUrl: 'view/admin-tmp.html'
                })
                .state('index.adminFinales', {
                    url:'/adminfinales',
                    templateUrl:'view/admin-new-matchs.html'
                })
                .state('index.classState', {
                    url: '/classement',
                    templateUrl: 'view/classement.html'
                })
                .state('unconnected.subscribeState', {
                    url: '/subscribe',
                    templateUrl: 'view/subscribe.html'
                })
                .state('index.confirmSubscription', {
                    url: '/confirmSubscription/:id',
                    templateUrl: 'view/confirmSubscription.html'
                })
                .state('index.profileState',{
                    url:'/profil',
                    templateUrl:'view/profil.html'
                })
              /*  .state('ligueState', {
                    url: '/ligue',
                    templateUrl: 'view/ligues.html'
                });*/

            $urlRouterProvider.otherwise('/index/bets');

            $httpProvider.interceptors.push(function ($q, $location, $injector) {


                var getAuthService = function () {
                    return $injector.get('authService');
                };
                var getState = function () {
                    return $injector.get('$state');
                };

                var isLoginState = function () {
                    return getState().current.name === 'unconnected.login';
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
            var adminRoute = 'index.adminState';
            var finalesState='index.adminFinales';
            var loginRoute = 'unconnected.login';
            var subscribeState = "unconnected.subscribeState";
            var confirmSubscription = "index.confirmSubscription";
            var betsState = "index.betsState";
            var adminRoutes = [adminRoute,finalesState]

            var adminAuthorized = function(routeName){
                return _.contains(adminRoutes,routeName);
            }

            var getMenuItem = function(state){
                if(state.name === 'index.betsState'){
                    return 'prono';
                }
                else if(state.name === 'index.classState'){
                    return 'classement';
                }
                else if(state.name === 'index.adminState'){
                    return 'results';
                }
                else if(state.name === 'index.adminFinales'){
                    return 'newmatchs';
                }
                return 'whatever';
            }

            $rootScope.$on('$stateChangeSuccess', function (evt, toState, toParams, fromState, fromParams) {

                var coreMenu = document.getElementById('core_menu');
                if(coreMenu){
                    coreMenu.setAttribute('selected', getMenuItem(toState));
                }

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
