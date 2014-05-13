/**
 * 
 */

zenFootDirectives=angular.module('zenFoot.directives',[]);



zenFootDirectives.directive('groupeDirective',function(){
    return {
        restrict:'E',
        replace:true,
        template:'view/groupe.html',
        scope:{
            groupe:'=groupe'
        }
    };
})

zenFootDirectives.directive('zenDisable',function(){
    return{
        scope:{
        },
        link:function(scope,element,attrs){
            scope.$watch(attrs.oldmatch,function(oldmatch){
                if(oldmatch) {
                    element.prop('disabled',true)
                }
            });
        }
    }
})


/*
zenFootDirectives.directive('zenToolTip',[function(){
    function link(scope,element,attrs){

    }

    return {

    }
}])
    */