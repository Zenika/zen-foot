'use strict';

var zenContactApp = angular.module('zenFoot.app',
    [ 'zenFoot.services', 'zenFoot.controllers', 'zenContact.filters',
        'ui.unique', 'zenFoot.directives', 'ngCookies',
        'ui.router', 'ngResource', 'ngRoute', 'angular-md5', 'ngGrid' ]);

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

 /*   $provide.factory('authInterceptor', function ($q, authService, $rootScope, $location) {
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

    $httpProvider.interceptors.push('authInterceptor');*/




});

zenContactApp.run(function ($rootScope, authService,$location) {

    var adminRoute="/admin"
    var loginRoute="/login"

    $rootScope.loggedIn = authService.loggedIn;
    $rootScope.logOut = authService.logOut;

    $rootScope.$on('$stateChangeStart',function(evt, toState, toParams, fromState, fromParams){


        console.log('ici')
        console.log($rootScope.isAdmin())

        if(!$rootScope.isConnected()&&toState.url!=loginRoute){
            toState.url=loginRoute;
        }
        else{
            if($rootScope.isAdmin()){
                console.log('redirecting admin')
                $location.path(adminRoute)
                console.log($location.path())
            }
        }
    })
});
