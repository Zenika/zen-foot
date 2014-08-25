'use strict';

angular.module('zenFoot.app')
    .factory('LigueService', ['$resource',
        function ($resource) {
            var isOwner = function (team, gambler) {
                return team.ownerEmail == gambler.email;
            };

            var getOwnerTeams = function (statutTeams, gambler) {
                var toRet = [];
                for (var x in statutTeams) {
                    if (isOwner(statutTeams[x].team, gambler)) {
                        toRet.push(statutTeams[x].team);
                    }
                }
                return toRet;
            };

            /**
             * Create objects linking a gambler with a team he wishes to join
             * @param statutTeams
             * @param gambler
             * @param gamblers
             */
            var gamblerTeam = function (statutTeams, gambler, gamblers) {
                var coupleArray = [];
                var ownerTeams = getOwnerTeams(statutTeams, gambler)
                for (var x in gamblers) {
                    var applicant = gamblers[x];
                    for (var y in applicant.statutTeams) {
                        var statutTeam = applicant.statutTeams[y];
                        if (isOwner(statutTeam.team, gambler)) {
                            coupleArray.push({gambler: applicant, statutTeam: statutTeam})
                        }
                    }
                }
                return coupleArray
                }

            var getAll = function () {
                return $resource('/api/teams').query()
            };

            var hasNewGroup = function (subscribedTeams) {

                for (var x in subscribedTeams) {

                    if (subscribedTeams[x].isNew) {
                        return true;
                    }
                }
                return false;

            };

            var quitTeam = function(){
                return $resource('api/quitTeam/:teamId',{teamId:'@teamId'});
            };

            return {
                isOwner: isOwner,
                getOwnerTeams: getOwnerTeams,
                gamblerTeam: gamblerTeam,
                getAll: getAll,
                hasNewGroup: hasNewGroup,
                quitTeam: quitTeam            }
        }])

    .factory('Team',['$resource',function($resource){
        return $resource('api/teams/:id',{id:'@id'});
    }])

    .factory('GamblerStatutTeam',function($resource){
        return $resource('api/gamblersAndTeam',{},{update:{method:'PUT',isArray:false}});
    })