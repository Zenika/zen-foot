/**
 * 
 */

'use strict';
var controllers = angular.module('controllers', ['ngResource']);

controllers.controller('SimpleController', [ '$scope', 'HelloNHour',
		'updateSentenceService', function($scope, HelloNHour) {
			$scope.sentence = HelloNHour.update({
				who : $scope.nom
			});
			$scope.$watch('nom', function(newValue) {
				$scope.sentence = HelloNHour.update({
					who : newValue
				});
			});

		} ]);

controllers.controller('LoginController', [
		'$scope',
		'authService',
		'$http',
		'$resource',
		function($resource,$scope, authService, $http, User, HelloNHour ) {
			$scope.login = function(user, password) {
				token=$resource('/api/auth/:user/:password', {
					user :user,
					password:password
				}).get();
				if(token){
					console.log(token);
				}
			};

			$scope.loggedIn = function() {
				return angular.isDefined(authService.loggedIn());
			};
		} ]);

controllers.controller('navBarController', function($scope, $location,
		isActiveService) {
	$scope.isActive = function(path) {
		isActiveService.isActive(path);
	}

});

controllers.controller('logOutController', function() {

});