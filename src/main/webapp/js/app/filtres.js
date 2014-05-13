/**
 * 
 */

var zenContactFilters=angular.module('zenContact.filters',[]);



zenContactFilters.filter('groupeFilter',[function(){
    return function(matchs,groupe){
       return triMatch(matchs,groupe,conditionMatchBet)
    };
}])


zenContactFilters.filter('matchFilter',[function(){
    return function(matchs, groupe){
        return triMatch(matchs,groupe,conditionMatch)
    }
}])

/**
 * The global algorithm for the filter
 * @param matchs
 * @param groupe
 * @param condition
 * @returns {Array}
 */
var triMatch=function(matchs,groupe,condition){
    var groupeList = [];

    angular.forEach(matchs,function(match){
        if(condition(match,groupe)){
            groupeList.push(match);
        }
    });

    return groupeList;
}

var conditionMatch=function(match, groupe){
    return match.participant1.groupe==groupe;
}

var conditionMatchBet=function(matchBet, groupe){
    return matchBet.match.participant1.groupe==groupe;
}

