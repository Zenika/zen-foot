/**
 * Created by raphael on 04/06/14.
 */
var zenfootModule = angular.module('zenFoot.app');

zenfootModule.controller('ProfilCtrl', ['$resource', 'Gambler', '$scope', 'Joiners', 'ProfilService', function ($resource, Gambler, $scope, Joiners, ProfilService) {

    $scope.gambler = Gambler.get();

    $scope.joiners = Joiners.getAll();


    /*   var isOwner = function (statutTeam) {
     return statutTeam.team.ownerEmail == $scope.gambler.email
     }
     */
    /*  $scope.getOwnerTeams = function () {
     var toRet = [];
     for (x in $scope.gambler.statutTeams) {
     if (isOwner($scope.gambler.statutTeams[x])) {
     toRet.push($scope.gambler.statutTeams[x].team);
     }
     }
     return toRet;
     }*/

    $scope.getOwnerTeams = function () {
        return ProfilService.getOwnerTeams($scope.gambler.statutTeams, $scope.gambler)
    }


    /**
     * Is the gambler part of this team ?
     * @param statutTeam
     * return true if the gambler is part of the team. Either as the owner or simple member.
     *
     */
    $scope.belongTo = function (statutTeam) {
        return statutTeam.accepted || ProfilService.isOwner(statutTeam, $scope.gambler)
    }


    /**
     * CHecks whether there are applicants or not. This method doesn't work.
     * @returns {boolean}
     */
    $scope.noApplicant = function () {
        if ($scope.joiners.length > 1)return false;

        var bool = false;
        for (var x in $scope.joiners) {
            var joiner = $scope.joiners[x]

            if (joiner.email != $scope.gambler.email) {
                console.log('not working')
                return false;
            }

        }
        console.log("icci")
        return true;
    }

    $scope.makeJoin = function (statutTeam) {
        statutTeam.accepted = true;
    }

    $scope.refuseJoin = function (joiner, statutTeam) {
        joiner.statutTeams.splice(statutTeam)
        Joiners.postJoiner(joiner)
    }


}])