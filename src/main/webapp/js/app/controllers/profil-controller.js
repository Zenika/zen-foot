/**
 * Created by raphael on 04/06/14.
 */
var zenfootModule = angular.module('zenFoot.app');

zenfootModule.controller('ProfilCtrl', ['$resource', 'Gambler', '$scope', function ($resource, Gambler, $scope) {

    $scope.gambler = Gambler.get();


    var isOwner = function (statutTeam) {
        return statutTeam.team.ownerEmail == $scope.gambler.email
    }
    $scope.getOwnerTeams = function () {
        var toRet = [];
        for (x in $scope.gambler.statutTeams) {
            if (isOwner($scope.gambler.statutTeams[x])) {
                toRet.push($scope.gambler.statutTeams[x].team);
            }
        }
        return toRet;
    }


    /**
     * Is the gambler part of this team ?
     * @param statutTeam
     * return true if the gambler is part of the team. Either as the owner or simple member.
     *
     */
    $scope.belongTo = function (statutTeam) {
        return statutTeam.accepted || isOwner(statutTeam)
    }

}])