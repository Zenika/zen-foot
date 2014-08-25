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

// Filters for matches :

/**
 * The global algorithm for the filter used to sort matches given their group
 * @param matchs
 * @param groupe
 * @param condition
 * @returns {Array}
 */
var triMatch = function (matchs, groupe, condition) {
    var groupeList = [];

    angular.forEach(matchs, function (match) {
        if (condition(match, groupe)) {
            groupeList.push(match);
        }
    });

    return groupeList;
}

/**
 * This filter, when turned on (apply==true), filters only matches whose score hasn't been
 * registered yet.
 */
zenContactFilters.filter('updatedMatchFilter', function () {

    return function (matchs, apply) {
        if (apply == true) {
            return triMatch(matchs, '', conditionUpdatedMatch)

        }
        else {
            return matchs;
        }
    }
})

var conditionUpdatedMatch = function (match) {
    return !match.scoreUpdated;
}

zenContactFilters.filter('passedMatchFilter', function () {
    return function (matchs) {
        return triMatch(matchs, '', conditionPassedMatch);
    }
})

var conditionPassedMatch = function (match, groupe) {
    return new Date(match.date) < new Date();
}


