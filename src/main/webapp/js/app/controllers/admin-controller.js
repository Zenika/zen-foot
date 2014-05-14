/**
 * Created by raphael on 12/05/14.
 */
angular.module('zenFoot.app')
.controller('AdminCtrl',['matchService', '$scope', 'SCORE_REGEXP','$resource',function(matchService, $scope,scoreRegexp,$resource){
        $scope.matchs = matchService.getAll();

        $scope.groupes = ["A", "B", "C", "D", "E", "F", "G", "H"];

        $scope.scoreRegexp=scoreRegexp;

        $scope.hasTwoScores=function(bet){
            var sc1Empty=!bet.score1.score||bet.score1.score.trim() ==""
            var sc2empty=!bet.score2.score||bet.score2.score.trim()=="";
            var notOk = (sc1Empty&&!sc2empty)||(!sc1Empty&&sc2empty)

            return notOk;
        }

        $scope.postez=function(){
            $resource('/api/matchs').save($scope.matchs,function(){
                console.log('matchs results posted!')
            },
            function(httpResponse){
                console.log(httpResponse);
            })
        }

    }])