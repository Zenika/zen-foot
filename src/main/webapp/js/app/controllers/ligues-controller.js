'use strict';

angular.module('zenFoot.app')
    .controller('LiguesCtrl', ['$resource', 'Gambler', '$scope', 'Joiners', 'LigueService', 'Team', '$modal', '$q',
        function ($resource, Gambler, $scope, Joiners, LigueService, Team, $modal, $q) {

            var isOwner = LigueService.isOwner;

            $scope.joiners = Joiners.getAll();


            /**
             * Called when the gambler and its teams are loaded from the server
             * @param promise
             */
            var getGambler = function (promise) {
                $q.all([promise, $scope.joiners.$promise])
                    .then(
                    function (results) {
                        $scope.gambler = results[0];
                        return results;
                    })
                    .then(
                    function (results) {
                        var gambler = results[0];
                        var joiners = results[1];
                        // Using a hash to map a statutTeam to its name. We also mark each statutTeam to know if the current gambler
                        // is the owner of the team
                        $scope.statutTeamByName = {};
                        for (var i = 0; i < gambler.statutTeams.length; i++) {
                            var statutTeam = gambler.statutTeams[i];
                            //We take advantage of the loop to index statutTeams by their names
                            $scope.statutTeamByName[statutTeam.team.name] = statutTeam;
                            if (isOwner(statutTeam, gambler)) {
                                statutTeam.isOwner = true;
                                statutTeam.demandes = 0;
                            }
                        }

                        //Calculating the demands on each team. We loop on each statutTeam of each joiner and see if there is a demand on
                        // a statutTeam that belongs to the connected gambler.
                        for (var i = 0; i < joiners.length; i++) {
                            var joiner = joiners[i];
                            if (joiner.id === gambler.id)continue;
                            for (var j = 0; j < joiner.statutTeams.length; j++) {
                                var statutTeam = joiner.statutTeams[j];
                                var statutTeamOwner = $scope.statutTeamByName[statutTeam.team.name];
                                if (!statutTeam.accepted && statutTeamOwner && statutTeamOwner.isOwner) {
                                    statutTeamOwner.demandes++;
                                }
                            }

                        }
                        return results;
                    }
                )
            }

            getGambler(Gambler.get().$promise);

            $scope.textDemandes = function (statutTeam) {
                var s = '';
                if (statutTeam.demandes > 1) {
                    s = 's';
                }

                if (statutTeam.demandes > 0) {
                    return '(' + statutTeam.demandes + ' demande' + s + ')';
                }
                else {
                    return '';
                }
            }


            $scope.getOwnerTeams = function () {
                return LigueService.getOwnerTeams($scope.gambler.statutTeams, $scope.gambler)
            };

            /**
             /**
             * CHecks whether there are applicants or not. This method doesn't work.
             * @returns {boolean}
             */
            $scope.noApplicant = function () {
                if ($scope.joiners.length > 1)return false;

                var bool = false;
                for (var i = 0; i < $scope.joiners.length; i++) {
                    var joiner = $scope.joiners[i];

                    if (joiner.email != $scope.gambler.email) {
                        return false;
                    }
                }
                return true;
            };

            $scope.makeJoin = function (joiner, statutTeam) {
                statutTeam.accepted = true;
                Joiners.postJoiner(joiner)
            };

            $scope.refuseJoin = function (joiner, statutTeam) {
                joiner.statutTeams.splice(statutTeam)
                Joiners.postJoiner(joiner)
            };


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

            initJoinedTeams();

            /**
             * Any already existing team
             */
            $scope.existingTeams = Team.getAll()

            $scope.pushTeam = function () {
                $scope.joinedTeams.push({name: "", isNew: false})
            };

            /**
             * Check there is no team with an empty name on $scope.joinedTeams, otherwise delete it
             */
            var checkTeams = function () {
                for (var i = 0; i < $scope.joinedTeams.length; i++) {
                    if ($scope.joinedTeams[i].name.trim() == "") {
                        $scope.joinedTeams.splice(i)
                    }
                }
            };

            var join = function () {
                checkTeams();
                var joinTeam = $resource('/api/gamblerAndTeam').save({gambler: $scope.gambler, teams: $scope.joinedTeams}, function (response) {
                    $scope.existingTeams = Team.getAll();
                    $scope.gambler = getGambler(response);
                    initJoinedTeams();
                });
            };

            var joinGroups = function () {
                var modalInstance = $modal.open({backdrop: 'static', scope: $scope, templateUrl: 'view/modal-sub-profil.html'})
                $scope.modalInstance = modalInstance

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
                for (var i = 0; i < $scope.gambler.statutTeams.length; i++) {
                    if($scope.gambler.statutTeams[i].team.name == statutTeam.team.name){
                        $scope.gambler.statutTeams.splice(i,1);
                        Gambler.update($scope.gambler,function(response){
                            var gambler = response[1];
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
                if (Team.hasNewGroup($scope.joinedTeams)) {
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

        }]);
