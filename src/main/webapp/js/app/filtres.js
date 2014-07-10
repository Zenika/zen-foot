/**
 *
 */

var zenContactFilters = angular.module('zenContact.filters', []);

zenContactFilters.filter('classementFilter',function(){
    return function(classement,search,threshold){
        if(!search) return [];
        var fuseFilter = new Fuse(classement,{
            keys:['nom','prenom'],
            threshold:threshold
        })
        return fuseFilter.search(search)
    }
})


// Filter logic to filter teams you are waiting for a confirmation


zenContactFilters.filter('waitTeamFilter',['ProfilService',function(ProfilService){
    return function(statutTeams, gambler){
        var toRet = [];
        for(x in statutTeams){
            var statutTeam = statutTeams[x];
            if(!ProfilService.isOwner(statutTeam.team,gambler)&&!statutTeam.accepted){
                toRet.push(statutTeam);
            }
        }
        return toRet;
    }
}])