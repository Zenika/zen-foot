'use strict';

angular.module('zenFoot.app')
    .controller('AdminCtrl', ['matchService', '$scope', 'SCORE_REGEXP', '$resource', '$filter', 'displayService',
        function (matchService, $scope, scoreRegexp, $resource, $filter, displayService) {

            $scope.matchs = matchService.getAll();

            $scope.groupes = ["A", "B", "C", "D", "E", "F", "G", "H"];

            $scope.scoreRegexp = scoreRegexp;

            $scope.hasTwoScores = function (bet) {
                var sc1Empty = !bet.score1.score || bet.score1.score.trim() == "";
                var sc2empty = !bet.score2.score || bet.score2.score.trim() == "";
                var notOk = (sc1Empty && !sc2empty) || (!sc1Empty && sc2empty);

                return notOk;
            };


            $scope.poster = function (match) {
                var date = $filter('date')(match.date, 'le dd/MM/yyyy à HH:mm');

                var message = "Etes vous sûr de vouloir enregistrer le match suivant :";
                message += "\n" + date;
                message += "\n" + match.participant1.pays + " : " + match.outcome.score1.score;
                message += "\n" + match.participant2.pays + " : " + match.outcome.score2.score;
                var result = confirm(message);
                if (!result || !match.id) return;
                $resource('/api/matchs/:id', {id: match.id}, {put: {method: 'PUT'}}).put(match, function () {
                    match.outcome.updated = true;
                });
            };


            $scope.cannotPost = function (match) {
                var score1 = match.outcome.score1.score;
                var score2 = match.outcome.score2.score;
                if ((!(score1)) || (!(score2)))return true;

                return match.outcome.score1.score.trim() == "" || match.outcome.score2.score.trim() == "";
            };

            $scope.isWinner = displayService.isWinner;

        }]);