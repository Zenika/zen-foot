'use strict';

angular.module('zenFoot.app')
    .controller('AdminCtrl', ['Match', '$scope', 'SCORE_REGEXP', '$resource', '$filter', 'displayService','$timeout',
        function (Match, $scope, scoreRegexp, $resource, $filter, displayService,$timeout) {

            $scope.matchs = Match.query();

            $scope.groupes = ["A", "B", "C", "D", "E", "F", "G", "H"];

            $scope.scoreRegexp = scoreRegexp;

            $scope.partiallyFilled = function (match) {
                var sc1Empty = match.score1==null||(''+match.score1).trim()=="";
                var sc2empty = match.score2==null||(''+match.score2).trim()=="";
                var partiallyFilled = (sc1Empty && !sc2empty) || (!sc1Empty && sc2empty);
                return partiallyFilled;
            };

            $scope.poster = function (match) {
                var date = $filter('date')(match.date, 'le dd/MM/yyyy à HH:mm');

                var message = "Etes vous sûr de vouloir enregistrer le match suivant :";
                message += "\n" + date;
                message += "\n" + match.team1 + " : " + match.score1;
                message += "\n" + match.team2 + " : " + match.score2;
                if(match.scoreUpdated){
                    message +='\n';
                    message += '\n attention ! Les résultats de ce match avaient déjà été enregistrés !';
                    message += '\n Les réenregistrer provoquera un recalcul de tous les scores des parieurs pour ce match.';
                    message += '\n A utiliser avec parcimonie en cas d\'erreur de saisie uniquement.';
                }
                var result = confirm(message);
                if (!result || !match.id) return;
                $resource('/api/matchs/:id', {id: match.id}, {put: {method: 'PUT'}}).put(match,
                    //success
                    function () {
                        match.scoreUpdated= true;
                        match.registered = true;
                        $timeout(function(){
                            delete match.registered;
                        },5000);
                    },
                    //error
                    function(){
                        match.error = true;
                        $timeout(function(){
                            delete match.error;
                        },5000);
                    }
                );
            };


            $scope.cannotPost = function (match) {
                var score1 = match.score1;
                var score2 = match.score2;
                if (score1==null||score2==null||(''+score1).trim()==""||(''+score2).trim()=="")return true;

                return (''+score1).trim() == "" || (''+score2).trim() == "";
            };

            $scope.isWinner = displayService.isWinner;

        }]);