'use strict';

angular.module('zenFoot.app')
.controller('AdminFinalesCtrl',['Pays','$scope',function(Pays,$scope){
    Pays.getPays().then(function(response){
        $scope.countries=response.data;
    })

    $scope.matches=[];

    $scope.newMatch=function(){
        var date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        var match={team1:'',team2:'',score1:null,score2:null,date:date};
        $scope.matches.push(match);
    }
    $scope.newMatch();

    $scope.format='dd/MM/yyyy';

    $scope.open = function($event) {
        $event.preventDefault();
        $event.stopPropagation();

        $scope.opened = true;
    };

    $scope.today = function() {
        $scope.dt = new Date(2014,7,2);
    };
    $scope.today();

    $scope.toggleMin = function() {
        $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

}])