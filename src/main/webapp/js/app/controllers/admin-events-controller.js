'use strict';

angular.module('zenFoot.app').controller('AdminEventsCtrl', ['$scope', 'AdminService', '$timeout', function ($scope, AdminService, $timeout) {

    $scope.archive = function () {

        var success = function (response) {
            $scope.archivedSuccess = true;
            delete $scope.eventName;
            $scope.registeredEvents = AdminService.eventNames();
            $timeout(function () {
                delete $scope.archivedSuccess;
            }, 5000);
        }

        var error = function (response) {
            $scope.archivedFail = true;
            $timeout(function () {
                delete $scope.archivedFail;
            }, 5000);
        }


        AdminService.archiveEvent($scope.eventName, success, error);
    }

    $scope.registeredEvents = AdminService.eventNames();


    $scope.invalidEventName = function(){
        return AdminService.invalidEventName($scope.registeredEvents, $scope.eventName);
    }

}])