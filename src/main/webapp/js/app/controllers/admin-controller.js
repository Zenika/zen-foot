/**
 * Created by raphael on 12/05/14.
 */
angular.module('zenFoot.app')
.controller('AdminCtrl',['matchService', '$scope', 'SCORE_REGEXP',function(matchService, $scope,scoreRegexp){
        $scope.matchs = matchService.getAll();

        $scope.groupes = ["A", "B", "C", "D", "E", "F", "G", "H"];

        $scope.scoreRegexp=scoreRegexp;


    }])