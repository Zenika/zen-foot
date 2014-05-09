'use strict';

var zenContactApp = angular.module('zenFoot.app',
    [ 'zenFoot.services', 'zenFoot.controllers', 'zenContact.filters',
        'ui.unique', 'zenFoot.directives', 'ngCookies',
        'ui.router', 'ngResource', 'ngRoute', 'angular-md5' ]);

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
        templateUrl: 'view/admin.html'
    });

    $urlRouterProvider.otherwise('/index');
    // zenContactApp.config(function($stateProvider, $urlRouterProvider,
    // $httpProvider) {

    // $urlRouterProvider.otherwise("/login");
    //
    // // $urlRouterProvider.when('*', function(){
    // // if(!authService.loggedIn()){
    // // return '/login';
    // // }
    // // else{
    // // return false;
    // // }
    // // });
    //
    // $stateProvider.state('indexState', {
    // url : "/index",
    // templateUrl : "view/index-content.html"
    // });
    //
    // $stateProvider.state('loginState',{
    // url:'/login',
    // templateUrl:"view/login.html"
    // });

    $provide.factory('authInterceptor', function ($q, authService, $rootScope, $location) {
        return {
            'responseError': function (rejection) {
                if (rejection.status == '401' || rejection.status == '403') {

                    if (rejection.status == '403' && $rootScope.user.isAdmin()) {
                        console.log('I\'m admin!');
                       // $location.path('/admin')
                    }

                    authService.redirectToLogin();
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

zenContactApp.run(function ($rootScope, authService) {
    $rootScope.loggedIn = authService.loggedIn;
    $rootScope.logOut = authService.logOut;
});
