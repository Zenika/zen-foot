'use strict';

var zenContactApp = angular.module('zenFoot.app',
    [ 'zenFoot.services', 'zenFoot.controllers', 'zenContact.filters',
        'ui.unique', 'zenFoot.directives', 'ngCookies',
        'ui.router', 'ngResource', 'ngRoute', 'angular-md5', 'ngGrid','ui.bootstrap' ]);

zenContactApp.config(function ($stateProvider, $urlRouterProvider, $httpProvider, $provide) {

    $stateProvider.state('loginState',
        {

            url: '/login',
            templateUrl: 'view/login.html'

        });
    $stateProvider.state('indexState', {
        url: '/index',
        templateUrl: 'view/index-content.html'
    });

    $stateProvider.state('adminState', {
        url: '/admin',
        templateUrl: 'view/admin-tmp.html'
    });

    $stateProvider.state('classState',{
        url:'/classement',
        templateUrl:'view/classement.html'
    })

    $urlRouterProvider.otherwise('/index');

    $provide.factory('authInterceptor', function ($q, authService, $rootScope, $location) {
        return {
            'responseError': function (rejection) {
                if (rejection.status == '401' || rejection.status == '403') {

                    if (rejection.status == '403' && $rootScope.isAdmin()) {
                        console.log('I\'m admin!');
                        $location.path('/admin')
                    }
                    else{
                        authService.redirectToLogin();

                    }

                }
                return $q.reject(rejection);

            },
            'response': function (response) {
                return response || $q.when(response);
            }
        };
    })

    $httpProvider.interceptors.push('authInterceptor');




});

zenContactApp.run(function ($rootScope, authService,$location,$state) {

    var adminRoute="adminState"
    var loginRoute="loginState"

    $rootScope.loggedIn = authService.loggedIn;
    $rootScope.logOut = authService.logOut;

    $rootScope.$on('$stateChangeSuccess',function(evt, toState, toParams, fromState, fromParams){


        console.log('ici')
        console.log($rootScope.user)
        console.log($rootScope.isAdmin())


        if($rootScope.isConnected()&&toState.name==loginRoute){
            console.log('login page is not auth. if connected')

            evt.preventDefault();
            $state.transitionTo(fromState.name)
        }

        if(!$rootScope.isConnected()&&toState.name!=loginRoute){
            console.log('redirecting to login')
            evt.preventDefault();
            $state.transitionTo(loginRoute)
        }
        else{
            console.log(toState)
            if($rootScope.isConnected()&&$rootScope.isAdmin()&&toState.name!=adminRoute){
                console.log('redirecting admin')
                evt.preventDefault();
                $state.transitionTo(adminRoute)
            }
        }
    })
});
