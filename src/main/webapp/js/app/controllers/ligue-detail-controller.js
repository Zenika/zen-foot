angular.module('zenFoot.app').controller('LigueDetailCtrl', ['$scope', 'Gambler', 'Gamblers', '$stateParams', 'Team', 'Joiners', '$q', 'LigueService', 'GamblerStatutTeam','GuestService',
    '$timeout',
    function ($scope, Gambler, Gamblers, $stateParams, Team, Joiners, $q, LigueService, GamblerStatutTeam, GuestService, $timeout) {

        $scope.gambler = Gambler.get();

        $scope.team = Team.get({id: $stateParams.id});

        $scope.joiners = Joiners.getFromTeam($stateParams.id);

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

        $q.all([Gamblers.query().$promise,$scope.joiners.$promise]).then(function(result){
            var gamblers = result[0];
            var members = result[1];
            var guestGamblers = intersection(members,gamblers);

            $scope.guestGamblers = guestGamblers;
        })


        $scope.gamblerName = function (gambler) {
            if (!gambler) return;
            return gambler.prenom + ' ' + gambler.nom;
        }

        $scope.invite = function(){
            var statutTeam  = {team:$scope.team};
            var gamblerStatutTeam = {statutTeam:statutTeam,gambler:$scope.guest};
            GuestService.invite(gamblerStatutTeam,function(){
                $scope.invited[$scope.guest.id] = $scope.guest;

                delete $scope.guest;

                $scope.lastGuests[gamblerStatutTeam.gambler.id] = gamblerStatutTeam.gambler;

                $timeout(function(){
                    delete $scope.lastGuests[gamblerStatutTeam.gambler.id];
                },8000);
            });
        }

        // LOGIC FOR APPLICANTS AND MEMBERS :

        /**
         * Says whether a gambler is the owner of the team, given a gambler
         * object and a statutTeam
         * @type {isOwner|*}
         */
        $scope.isOwner = LigueService.isOwner;

        $q.all([$scope.joiners.$promise,$scope.team.$promise]).then(function (result) {
            var joiners = result[0];
            var currentTeam = result[1];

            $scope.statutTeamByGambler = {};

            //building map which binds a gambler to the current page statutTeam
            for (var i = 0; i < joiners.length; i++) {
                var joiner = joiners[i];
                for (var j = 0; j < joiner.statutTeams.length; j++) {
                    var statutTeam = joiner.statutTeams[j];
                    if (statutTeam.team.name = currentTeam.name) {
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

        })

        /**
         * Called for an applicant who whishes to join the ligue, when they're accepted
         * @param applicant
         */
        $scope.acceptInTeam = function (applicant) {
            var statutTeam = $scope.statutTeamByGambler[applicant.id];
            statutTeam.accepted = true;
            var gamblerStatutTeam = {gambler: applicant, statutTeam: statutTeam};
            GamblerStatutTeam.update(gamblerStatutTeam, function (response) {
                delete $scope.applicants[applicant.id];
                $scope.members[applicant.id] = applicant;
            });
        }

        /**
         * Called for an applicant who whishes to join the ligue, when they're refused
         * @param applicant
         */
        $scope.refuseInTeam = function (applicant) {
            var statutTeam = $scope.statutTeamByGambler[applicant.id];
            statutTeam.accepted = false;
            var gamblerStatutTeam = {gambler: applicant, statutTeam: statutTeam};

            GamblerStatutTeam.update(gamblerStatutTeam, function(response){
                delete $scope.applicants[applicant.id];
            });
        }

        /**
         * Remove a member from a ligue he/she is already part of
         * @param member
         */
        $scope.removeFromTeam = function(member){
            var statutTeam = $scope.statutTeamByGambler[member.id];
            statutTeam.accepted = false;
            var gamblerStatutTeam = {gambler: member, statutTeam: statutTeam};

            GamblerStatutTeam.update(gamblerStatutTeam, function(response){
                delete $scope.members[member.id];
            });
        }

    }])