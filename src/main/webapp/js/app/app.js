'use strict';

var zenContactApp = angular.module('zenContact.app', [ 'zenContact.services',
		'controllers', 'zenContact.filters', 'ui.unique',
		'zenContact.directives', 'ngCookies', 'ngRoute', 'ui.router' ]);

zenContactApp.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("list/cartes");

	$stateProvider.state('listState', {
		url : "/list",
		templateUrl : "view/list.html",
		controller : 'ContactListController'
	});

});

// zenContactApp.config(function($routeProvider, $httpProvider) {
// $routeProvider.when('/list', {
// templateUrl : 'view/list.html',
// controller : 'ContactListController'
// });
// $routeProvider.when('/edit', {
// templateUrl : 'view/edit.html',
// controller : 'ContactAddController'
// });
//
// $routeProvider.when('/edit/:id', {
// templateUrl : 'view/edit.html',
// controller : 'ContactEditController'
// });
//
// $routeProvider.when('/login', {
// templateUrl : 'view/login.html'
//
// });
//
// $routeProvider.otherwise({
// redirectTo : '/list'
// });

// $httpProvider.interceptors.push(function($q, authService) {
// return {
// 'responseError' : function(rejection) {
// if (rejection.status == '401') {
// authService.redirectToLogin();
// }
// return $q.reject(rejection);
//
// },
// 'response' : function(response) {
// authService.storeToken(response);
// return response || $q.when(response);
// }
// };
// });
//
// });

zenContactApp.run(function($rootScope, authService) {
	$rootScope.loggedIn = authService.loggedIn;
	$rootScope.logOut = authService.logOut;
});
