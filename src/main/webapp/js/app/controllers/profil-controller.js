/**
 * Created by raphael on 04/06/14.
 */
var zenfootModule = angular.module('zenFoot.app');

zenfootModule.controller('ProfilCtrl', ['$resource', 'Gambler', '$scope', 'Joiners', 'ProfilService','Team','$modal', function ($resource, Gambler, $scope, Joiners, ProfilService,Team,$modal) {

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

    $scope.isOwner = ProfilService.isOwner

    /**
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
                return false;
            }
        }
        return true;
    }

    $scope.makeJoin = function (joiner,statutTeam) {
        statutTeam.accepted = true;
        Joiners.postJoiner(joiner)
    }

    $scope.refuseJoin = function (joiner, statutTeam) {
        joiner.statutTeams.splice(statutTeam)
        Joiners.postJoiner(joiner)
    }




    //Logic to create/join a group

    $scope.joinedTeams = [{name:"",isNew:false}];

    $scope.existingTeams=Team.getAll()

    $scope.pushTeam=function(){
        $scope.joinedTeams.push({name:"",isNew:false})
    }
    var checkTeams= function (){
        for(var x in $scope.joinedTeams){
            if($scope.joinedTeams[x].name.trim()==""){
                $scope.joinedTeams.splice(x)
            }
        }
    }

    var join = function() {
        checkTeams();
        var joinTeam = $resource('/api/gambler').save({gambler:$scope.gambler,teams:$scope.joinedTeams},function(response){
            $scope.existingTeams=Team.getAll()
            $scope.gambler = response
        });
    };

    var joinGroups= function(){
        var modalInstance = $modal.open({backdrop:'static',scope:$scope,templateUrl:'view/modal-sub-profil.html'})
        $scope.modalInstance= modalInstance

        modalInstance.result.then(function(response){
            if(response==true){
                join()
            }
        })
    }

    $scope.valider=function(){
        if(Team.hasNewGroup($scope.joinedTeams)){
            joinGroups()
        }
        else{
            join()
        }

    }

    $scope.ok=function(){
        $scope.modalInstance.close(true);
    }

    $scope.cancel=function(){
        $scope.modalInstance.dismiss();
    }
}])