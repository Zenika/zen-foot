/**
 *
 */

zenFootDirectives = angular.module('zenFoot.directives', []);


zenFootDirectives.directive('groupeDirective', function () {
    return {
        restrict: 'E',
        replace: true,
        template: 'view/groupe.html',
        scope: {
            groupe: '=groupe'
        }
    };
})

zenFootDirectives.directive('zenDisable', function () {
    return{
        scope: {
        },
        link: function (scope, element, attrs) {
            scope.$watch(attrs.oldmatch, function (oldmatch) {
                if (oldmatch) {
                    element.prop('disabled', true)
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

zenFootDirectives.directive('popoverInfo', function () {
    return {
        link: function (scope, element, attrs) {
            element.on('mouseover', function (event) {
                event.preventDefault();
                element.popover({trigger: 'click'});
            })
        }
    }
})

zenFootDirectives.directive('groupeLabel', function () {
    return {
        replace: true,
        restrict: 'E',
        link: function (scope, element, attrs) {
            var template = "<label class='btn btn-primary' ng-model='checkModel' btn-radio='"+attrs.groupe+"' btn-checkbox>"+attrs.groupe+"</label>";
            element.replaceWith(template)
        }
    }
})