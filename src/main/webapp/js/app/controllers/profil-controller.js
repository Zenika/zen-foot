/**
 * Created by raphael on 04/06/14.
 */
var zenfootModule = angular.module('zenFoot.app');

zenfootModule.controller('ProfilCtrl',['$resource','Gambler','$scope',function($resource,Gambler,$scope){

    $scope.gambler=Gambler.get();


    $scope.getOwnerTeams=function(){
        var toRet = [];
        for(x in $scope.gambler.teams){
            if($scope.gambler.teams[x].ownerEmail==$scope.gambler.email){
                toRet.push($scope.gambler.teams[x]);
            }
        }
        return toRet;
    }

}])