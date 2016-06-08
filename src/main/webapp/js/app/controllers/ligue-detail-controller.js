angular.module('zenFoot.app').controller('LigueDetailCtrl', ['$scope', '$stateParams', '$q', 'LigueService',
    '$timeout', 'Events',
    function ($scope, $stateParams, $q, LigueService, $timeout, Events) {

        //$scope.gambler = Gambler.get();

        //$scope.team = Team.get({id: $stateParams.id});

        //$scope.joiners = Joiners.getFromTeam($stateParams.id);

        //The last invited guests : used to display messages
        $scope.lastGuests = {};

        // LOGIC FOR GUESTS :

        /**
         * Efficient method for the intersecction of two arrays : allGamblers - members
         * Adapted from http://stackoverflow.com/questions/1187518/javascript-array-difference
         * @param members
         * @param allGamblers
         * @returns {Array}
         */
        var intersection = function(members, allGamblers)
        {
            var a = {};
            for (var i = 0; i < members.length; i++)
                a[members[i].id] = true;
            for (var i = allGamblers.length-1; i > -1; i--)
                if (a[allGamblers[i].id]){
                    allGamblers.splice(i, 1);
                }

            return allGamblers;
        }


        $scope.gamblerName = function (gambler) {
            if (!gambler) return;
            return gambler.firstName + ' ' + gambler.lastName;
        }

        // LOGIC FOR APPLICANTS AND MEMBERS :

        /*$q.all([$scope.joiners.$promise,$scope.team.$promise]).then(function (result) {
            var joiners = result[0];
            var currentTeam = result[1];

            $scope.statutTeamByGambler = {};

            //building map which binds a gambler to the current page statutTeam
            for (var i = 0; i < joiners.length; i++) {
                var joiner = joiners[i];
                for (var j = 0; j < joiner.statutTeams.length; j++) {
                    var statutTeam = joiner.statutTeams[j];
                    if (statutTeam.team.name == currentTeam.name) {
                        $scope.statutTeamByGambler[joiner.id] = statutTeam;
                    }
                }
            }

            $scope.members = {};

            $scope.applicants = {};

            $scope.invited = {};

            for (var i = 0; i < $scope.joiners.length; i++) {
                var key = $scope.joiners[i];
                var statutTeam = $scope.statutTeamByGambler[key.id];
                var isOwner = $scope.isOwner(statutTeam.team, key);
                if (statutTeam.accepted || isOwner) {
                    statutTeam.isOwner = isOwner;
                    $scope.members[key.id] = key;
                }
                else {
                    if(!statutTeam.invitation){
                        $scope.applicants[key.id] = key;
                    }
                    else{
                        $scope.invited[key.id] = key;
                    }
                }

            }

        })*/

        /**
         * Called for an applicant who whishes to join the ligue, when they're accepted
         * @param applicant
         */
        $scope.acceptInTeam = function (applicant, $index) {
            $scope.ligue.awaits.splice($index, 1);
            if ($scope.ligue.accepted === null || $scope.ligue.accepted === undefined)
                $scope.ligue.accepted = [];
            $scope.ligue.accepted.push(applicant);
            
            Events.updateLigue({id : $scope.selectedEvent.id, idLigue : $stateParams.id}, $scope.ligue);
        }

        /**
         * Called for an applicant who whishes to join the ligue, when they're refused
         * @param applicant
         */
        $scope.refuseInTeam = function (applicant, $index) {
            $scope.ligue.awaits.splice($index, 1);
            Events.updateLigue({id : $scope.selectedEvent.id, idLigue : $stateParams.id}, $scope.ligue);
        }

        /**
         * Remove a member from a ligue he/she is already part of
         * @param member
         */
        $scope.removeFromTeam = function(member, $index){
            $scope.ligue.accepted.splice($index, 1);
            Events.updateLigue({id : $scope.selectedEvent.id, idLigue : $stateParams.id}, $scope.ligue);
        }
        
        
        var initData = function () {
            $scope.ligue = Events.getLigue({id : $scope.selectedEvent.id, idLigue : $stateParams.id});
        }
            
        //si deja selectionné
        if($scope.selectedEvent != undefined) {
            initData();
        }
        
        $scope.$on('eventChanged', function(event, params) {
            initData();
        })

    }])