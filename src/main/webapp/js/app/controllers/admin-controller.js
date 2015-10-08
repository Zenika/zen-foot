'use strict';

angular.module('zenFoot.app')
    .controller('AdminCtrl', ['$scope', '$resource', '$filter', 'displayService','$timeout', 'Events',
        function ($scope, $resource, $filter, displayService,$timeout, Events) {
            
            $scope.events = Events.query();

            $scope.scoreRegexp = /^[0-9]{1,2}$|^$/;

            $scope.partiallyFilled = function (match) {
                var score1NotFilled = match.score1==null || match.score1 == '';
                var score2NotFilled = match.score2==null || match.score2 == '';
                var partiallyFilled = (score1NotFilled && !score2NotFilled) || (!score1NotFilled && score2NotFilled);
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
                Events.updateMatchScore({id: $scope.event.id}, match,
                    function success() {
                        match.scoreUpdated= true;
                        match.registered = true;
                        $timeout(function(){
                            delete match.registered;
                        },5000);
                    },
                    function error(){
                        match.error = true;
                        $timeout(function(){
                            delete match.error;
                        },5000);
                    }
                );
            };

            $scope.cannotPost = function (match) {
                return match.score1 == undefined || match.score2 == undefined || match.score2 == '' || match.score1 == '';
            };

            $scope.isWinner = displayService.isWinner;
            
            $scope.matchs = []
            var success = function(response) {
                $scope.matchs = response;
            }
            $scope.loadMatches = function() {
                
                Events.matches({id : $scope.event.id}, success);
            }

            /*
                Is a match removable ?
                Impossible to remove an edited match
            */
            $scope.isNotRemovable = function (match) {
                return match.scoreUpdated;
            };

            /*
                Remove a match from an event
            */
            $scope.remove = function (match) {
                Events.removeAMatch({idEvent : $scope.event.id, idMatch : match.id},
                    function (){
                        updateMatches(match)
                    }
                );

                function updateMatches(match) {
                   $scope.matchs.splice($scope.matchs.indexOf(match),1);
                };
            };

        }]);