/**
 * 
 */

var zenContactFilters=angular.module('zenContact.filters',[]);



zenContactFilters.filter('groupeFilter',[function(){
    return function(matchs,groupe){
        var groupeList = [];

        angular.forEach(matchs,function(match){
            if(match.match.participant1.groupe==groupe){
                groupeList.push(match);
            }
        });

        return groupeList;
    };
}])