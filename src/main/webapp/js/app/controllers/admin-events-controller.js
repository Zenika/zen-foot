'use strict';

angular.module('zenFoot.app').controller('AdminEventsCtrl', ['$scope', 'Events', '$timeout', 'AdminService',
    function ($scope, Events, $timeout, AdminService) {
         
    var success = function (callback) {
        var others = arguments;
        return function (response) {
            $scope.actionSucceed = true;
            $timeout(function () {
                delete $scope.actionSucceed;
                delete $scope.action;
            }, 5000);
            if(callback != null && callback != undefined) {
                callback(others);
            }
        }
    };
    var error = function (callback) {
        var others = arguments;
        return function (response) {
            $scope.actionFailed = true;
            $timeout(function () {
                delete $scope.actionFailed;
                delete $scope.action;
            }, 5000);
            if(callback != null && callback != undefined) {
                callback(others);
            }
        }
    }
    var dateCheck = function(start, end) {
        if(end <= start) {
            $scope.dateCheckFailed = true; 
            $timeout(function () {
                delete $scope.dateCheckFailed;
            }, 5000);
            return false;
        }
        return true;
    }
        
    $scope.today = new Date();
    $scope.events = Events.query();
    $scope.modeCreate = true;
    $scope.modeUpdate = false;

    $scope.initEvent = function () {
        $scope.event = {name: '', start: null, end: null};
    }
    $scope.initEvent();

    $scope.update = function () {
        $scope.action = 'modification';

        if(!dateCheck($scope.event.start, $scope.event.end)) return;
        
        $scope.events[$scope.indexModification].start = $scope.event.start;
        $scope.events[$scope.indexModification].end = $scope.event.end;
        
        $scope.events[$scope.indexModification].$update().then(success()).catch(error());
        
        $scope.cancel();
    }

    $scope.save = function () {
        $scope.action = 'création'

        var callback = function(index) {
            $scope.events.push(newEvent);
        }

        if(!dateCheck($scope.event.start, $scope.event.end)) return;
        var newEvent = new Events();
        angular.copy($scope.event, newEvent);
        newEvent.$save().then(success(callback, newEvent)).catch(error());
        
        $scope.initEvent();
    }
    
    $scope.startUpdate = function(index, event) {
        $scope.indexModification = index;
        event.start = new Date(event.start); // conversion en objet de type date, sinon probleme au check dateValid
        event.end = new Date(event.end)// conversion en objet de type date, sinon probleme au check dateValid
        angular.copy(event, $scope.event);
        $scope.modeCreate = false;
        $scope.modeUpdate = true;
    }
    
    $scope.cancel = function(event) {
        $scope.initEvent();
        $scope.modeCreate = true;
        $scope.modeUpdate = false;
        delete $scope.indexModification;
    }
    
    $scope.invalidEventName = function(){
        return $scope.modeCreate && AdminService.invalidEventName($scope.events, $scope.event.name);
    }
    $scope.removeEvent = function(index, event) {
        $scope.action = 'suppression'
        var callback = function(index) {
            $scope.events.splice(index, 1);
        }

        event.$remove().then(success(callback, index)).catch(error());
    };

}])