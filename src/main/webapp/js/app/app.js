'use strict';

var zenContactApp = angular.module('zenContact.app',
		[ 'zenContact.services', 'controllers', 'zenContact.filters',
				'ui.unique', 'zenContact.directives', 'ngCookies', 'ngRoute',
				'ui.router', 'ngRoute', 'ngResource' ]);

zenContactApp.config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/login', {
		templateUrl : 'view/login.html',
		controller : 'LoginController'
	});
	$routeProvider.when('/index', {
		templateUrl : 'view/index-content.html',
		controller : 'ContactListController'
	});

	$routeProvider.otherwise({
		redirectTo : '/login'
	});
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
				authService.storeToken(response);
				return response || $q.when(response);
			}
		};
	});

});

zenContactApp.run(function($rootScope, authService) {
	$rootScope.loggedIn = authService.loggedIn;
	$rootScope.logOut = authService.logOut;
});
