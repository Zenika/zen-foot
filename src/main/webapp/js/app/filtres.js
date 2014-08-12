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
