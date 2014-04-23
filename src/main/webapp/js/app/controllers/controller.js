/**
 * 
 */

'use strict';
var controllers = angular.module('zenFoot.controllers', ['ngResource']);

controllers.controller('HelloCtrl', [ '$scope','$resource',
		function($scope, $resource) {

			$scope.sentence= $resource('/api/:param',{param:'coucou'}).get();

		} ]);







controllers.controller('navBarController', function($scope, $location,
		isActiveService) {
	$scope.isActive = function(path) {
		isActiveService.isActive(path);
	}

});

