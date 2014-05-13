/**
 * Created by raphael on 13/05/14.
 */
var zenFootService = angular.module("zenFoot.services");
zenFootService.factory('matchService',['$resource',function($resource){
    return {

        getAll:function(){
            return $resource('/api/matchs').query();
        }
    }
}])