'use strict';
/**
 * Created by raphael on 25/08/14.
 */
angular.module('zenFoot.app').factory('Pays',['$http',function($http){
    return {
        getPays:function(){
            return $http({method:'GET',url:'/api/pays'});
        }
    }
}])
