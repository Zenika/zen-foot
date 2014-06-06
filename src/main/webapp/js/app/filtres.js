/**
 *
 */

var zenContactFilters = angular.module('zenContact.filters', []);


zenContactFilters.filter('groupeFilter', [function () {


    return function (matchs,showAll, groupe) {
        if(showAll){
            return matchs;
        }
        else {
            return triMatch(matchs, groupe, conditionMatchBet)
        }
    };
}])


zenContactFilters.filter('matchFilter', [function () {
    return function (matchs, groupe) {
        return triMatch(matchs, groupe, conditionMatch)
    }
}])

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
 *
 * In case you don't want to apply the filter, which is the case as we conditionally apply them sometimes.
 * @param matchs
 * @returns all matchs
 */
var returnAll=function(matchs){
    return matchs;
}


var conditionMatch = function (match, groupe) {
    return match.participant1.groupe == groupe;
}

var conditionMatchBet = function (matchBet, groupe) {
    return matchBet.match.participant1.groupe == groupe;
}

zenContactFilters.filter('passedMFilter', function () {
    return function (matchs) {
        return triMatch(matchs, '', conditionPassedM);
    }
})

var conditionPassedM = function (match, groupe) {
    return new Date(match.date) < new Date();
}


/**
 * This filter, when turned on (apply==true), filters only matches whose score hasn't been
 * registered yet.
 */
zenContactFilters.filter('updatedMFilter', function () {


    return function (matchs, apply) {
        if (apply == true) {
            return triMatch(matchs, '', conditionUpdatedM)

        }
        else {
            return matchs;

        }
    }
})

var conditionUpdatedM = function (match) {

    return !match.outcome.updated;
}

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
            if(!ProfilService.isOwner(statutTeam,gambler)&&!statutTeam.accepted){
                toRet.push(statutTeam);
            }
        }
        return toRet;
    }
}])