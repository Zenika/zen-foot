angular.module('zenFoot.app').controller('LigueDetailCtrl', ['$scope','Gambler',function ($scope,Gambler) {

    $scope.gambler = Gambler.get();

}])