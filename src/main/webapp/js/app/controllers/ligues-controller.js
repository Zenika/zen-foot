'use strict';

angular.module('zenFoot.app')
    .controller('LiguesCtrl', ['$resource', '$scope', '$modal', '$q', 'Events', 'LigueService', 
        function ($resource, $scope, $modal, $q, Events, LigueService) {

            $scope.ligue_regexp = /^[A-Za-z0-9\s]{1,20}$/;

            $scope.textDemandes = function (ligue) {
                var s = '';
                if (ligue.awaits != undefined && ligue.awaits > 1) {
                    s = 's';
                }

                if (ligue.awaits != undefined && ligue.awaits > 0) {
                    return '(' + ligue.awaits + ' demande' + s + ')';
                }
                else {
                    return '';
                }
            }


            //Logic to create/join/quit a group

            /**
             * Initialize the $scope.joinedTeams variable, which represents the teams the connected gambler would like
             * to join or create
             */
            var initJoinedTeams = function () {
                $scope.joinedTeams = [
                    {name: "", isNew: false}
                ];
            }

            /**
             * Any already existing team
             */

            $scope.pushTeam = function () {
                $scope.joinedTeams.push({name: "", isNew: false})
            };

            /**
             * Check there is no team with an empty name on $scope.joinedTeams, otherwise delete it
             */
            var deleteEmptyNameTeams = function () {
                for (var i = $scope.joinedTeams.length-1; i > -1 ; i--) {
                    if ($scope.joinedTeams[i].name.trim() == "") {
                        $scope.joinedTeams.splice(i);
                    }
                }
            };
            
            var onJoinOrCreateLigueSuccess = function () {
                $scope.numberOfLigueHandled++;
                if ($scope.numberOfLigueHandled == $scope.numberOfLigueToHandle){
                    console.log("ok")
                    initData();
                }
            }
                
            var joinLigue = function (team) {
                var ligue = _.find($scope.ligues, function (regTeam) {
                    return regTeam.name == team.name;
                });
                Events.joinLigues({id : $scope.selectedEvent.id, 
                    idLigue : ligue.id}, onJoinOrCreateLigueSuccess);
            }
                
            var createLigue = function (team) {
                Events.createLigues({id : $scope.selectedEvent.id}, team, onJoinOrCreateLigueSuccess);
            }

            var join = function () {
                deleteEmptyNameTeams();
                var ligueToCreate = _.filter($scope.joinedTeams, function(team) { return team.isNew});
                var ligueToJoin = _.filter($scope.joinedTeams, function(team) { return !team.isNew});
                
                $scope.numberOfLigueToHandle = $scope.joinedTeams.length;
                
                _.each(ligueToCreate, createLigue);
                _.each(ligueToJoin, joinLigue);
            };

            var joinGroups = function () {
                var modalInstance = $modal.open({backdrop: 'static', scope: $scope, templateUrl: 'view/modal-sub-profil.html'})
                $scope.modalInstance = modalInstance;

                modalInstance.result.then(function (response) {
                    if (response == true) {
                        join()
                    }
                })
            };

            /**
             * Called when the gambler clicks on the delete icon aside a ligue
             * @param statutTeam
             */
            $scope.quitLigue = function (statutTeam) {
                if(!statutTeam) return;
                for (var i = $scope.gambler.statutTeams.length-1; i > -1 ; i--) {
                    if($scope.gambler.statutTeams[i].team.name == statutTeam.team.name){
                        $scope.gambler.statutTeams.splice(i,1);
                        LigueService.quitTeam().get({teamId:statutTeam.team.id},function(response){
                            var gambler = response;
                            $scope.gambler = getGambler(gambler);
                        });
                        break;
                    }
                }
            }


            /**
             * "valider" button click action, when the gambler has entered all the teams they'd like to join/create
             */
            $scope.valider = function () {
                if (LigueService.hasNewGroup($scope.joinedTeams)) {
                    joinGroups()
                }
                else {
                    join()
                }
            };

            $scope.ok = function () {
                $scope.modalInstance.close(true);
            };

            $scope.cancel = function () {
                $scope.modalInstance.dismiss();
            }
            
            var initData = function () {
                initJoinedTeams();
                $scope.ligues = Events.getLigues({id : $scope.selectedEvent.id});
                $scope.numberOfLigueHandled = 0;
            }
            
            $scope.hasLigue = function() {
                return _.find($scope.ligues, function(ligue) {
                    ligue.isAccepted || ligue.isAwaits || ligue.isOwner;
                }) != undefined
            }
            
            //si deja selectionné
            if($scope.selectedEvent != undefined) {
                initData();
            }
            $scope.$on('eventChanged', function(event, params) {
                initData();
            })

        }]);
