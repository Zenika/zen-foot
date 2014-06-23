'use strict';

angular.module('zenFoot.app')
.controller('PersoRankingCtrl',['$stateParams','GamblerService','$scope',function($stateParams,GamblerService,$scope){

        $scope.otherGambler = GamblerService.getFromId($stateParams.gamblerId)
    }])