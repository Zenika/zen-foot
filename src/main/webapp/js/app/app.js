'use strict';

var zenContactApp = angular.module('zenFoot.app',
		[ 'zenFoot.services', 'zenFoot.controllers', 'zenContact.filters',
				'ui.unique', 'zenFoot.directives', 'ngCookies',
				'ui.router', 'ngResource', 'ngRoute', 'angular-md5' ]);

zenContactApp.config(function($stateProvider, $urlRouterProvider, $httpProvider) {

	$stateProvider.state('loginState',
        {

        url:'/login',
		templateUrl : 'view/login.html'

	});
	$stateProvider.state('indexState', {
        url:'/index',
		templateUrl : 'view/index-content.html'
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

	$httpProvider.interceptors.push(function($q, authService) {
		return {
			'responseError' : function(rejection) {
				if (rejection.status == '401') {
					authService.redirectToLogin();
				}
				return $q.reject(rejection);

			},
			'response' : function(response) {
				return response || $q.when(response);
			}
		};
	});

});

zenContactApp.run(function($rootScope, authService) {
	$rootScope.loggedIn = authService.loggedIn;
	$rootScope.logOut = authService.logOut;
});
