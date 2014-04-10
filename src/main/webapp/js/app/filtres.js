/**
 * 
 */

var zenContactFilters=angular.module('zenContact.filters',[]);



zenContactFilters.filter('fuzzyFilter', function () {
	  return function (contacts, search, threshold) {
	    if (!search) {
	      return contacts;
	    }

	    var fuse = new Fuse(contacts, {
	    	keys: ['firstName', 'lastName'],
	      	threshold: threshold
	    });
	    
	    return fuse.search(search);
	  };
	});