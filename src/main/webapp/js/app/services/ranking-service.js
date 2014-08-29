/**
 * Created by raphael on 11/06/14.
 */
angular.module('zenFoot.app')
.factory('RankingService',['$resource',function($resource){
        return {
            getAll:function(){
                return $resource('/api/rankings').query();
            },
            myRanking:function(){
                return $resource('/api/ranking').get();
            }
        }
    }])