'use strict';

var controllers = angular.module('zenFoot.app')
    .controller('navBarController', ['$scope', '$location', 'isActiveService',
        function ($scope, $location, isActiveService) {
            $scope.isActive = function (path) {
                isActiveService.isActive(path);
            }

        }]);

