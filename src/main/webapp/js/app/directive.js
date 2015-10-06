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
});

zenFootDirectives.directive('zenDisable', function () {
    return{
        scope: {
        },
        link: function (scope, element, attrs) {
            scope.$watch(attrs.oldmatch, function (oldmatch) {
                if (oldmatch) {
                    element.prop('disabled', true);
                }
            });
        }
    }
});

zenFootDirectives.directive('popoverInfo', function () {
    return {
        link: function (scope, element, attrs) {
            element.on('mouseover', function (event) {
                event.preventDefault();
                element.popover({trigger: 'click'});
            })
        }
    }
});

zenFootDirectives.directive('groupeLabel', function () {
    return {
        replace: true,
        restrict: 'E',
        link: function (scope, element, attrs) {
            var template = "<label class='btn btn-primary' ng-model='checkModel' btn-radio='" + attrs.groupe + "' btn-checkbox>" + attrs.groupe + "</label>";
            element.replaceWith(template);
        }
    }
});

zenFootDirectives.directive('pwdCheck', [function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
            var firstPassword = '#' + attrs.pwdCheck;
            elem.add(firstPassword).on('keyup', function () {
                scope.$apply(function () {
                    var v = elem.val() === $(firstPassword).val();
                    ctrl.$setValidity('pwdmatch', v);
                });
            });
        }
    }
}]);

zenFootDirectives.directive('generateInput', function () {
    return{
        link: function (scope, element, attrs) {
            scope.$watch("teamForm.$pristine", function (newValue, oldValue) {
                if (newValue != oldValue) {
//                    scope.subscriber.teams.push({name: ''})
                    scope.pushTeam();
                }
            })
        }}
});

zenFootDirectives.directive('newTeam', function () {

    var isNew = function (team, regTeams) {
        if(!team.name)return;
        var result = _.find(regTeams, function (regTeam) {
            return regTeam.name == team.name;
        });
        team.isNew = (result == undefined) && team.name.trim() != "";
    };
    return {
        link: function (scope, element, attrs) {
            scope.$watch("team.name", function (newValue, oldValue) {
                if (newValue != oldValue) {
                    isNew(scope.team, scope.ligues);
                }
            })
        }
    }
});

zenFootDirectives.directive('selectLigue', function () {
    return {
        link: function ($scope, element, attrs) {
            $scope.$watch('selectedLigue', function (newValue, oldValue) {
                if (newValue != oldValue) {
                    $scope.initData();
                }
            });
        }
    }
});

var ZENIKA_EMAIL_REGEXP = /^.*@zenika\.com$/;

zenFootDirectives.directive('zenMail', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (viewValue) {
                if (ZENIKA_EMAIL_REGEXP.test(viewValue)) {
                    // it is valid
                    ctrl.$setValidity('zenikaEmail', true);
                    return viewValue;
                } else {
                    // it is invalid, return undefined (no model update)
                    ctrl.$setValidity('zenikaEmail', false);
                    return undefined;
                }
            });
        }
    };
});

zenFootDirectives.directive('todayFocus', ['$location', '$anchorScroll', function ($location, $anchorScroll) {
    return{
        link: function (scope, element, attrs) {
            var dateMatch = new Date(parseInt(scope.key, 10));
            var today = new Date();

            if (today.getFullYear() !== dateMatch.getFullYear()) {
                return;
            }
            else {
                if (today.getMonth() !== dateMatch.getMonth()) {
                    return;
                }
                else {
                    if (today.getDate() !== dateMatch.getDate()) {
                        return;
                    } else {
                        element.attr('id', 'todayFocused')
                    }
                }
            }
        }
    }
}])

zenFootDirectives.directive('dateValid',[function(){
    return {
        require:'ngModel',
        link:function(scope,element,attrs,ctrl){
            scope.$watch(attrs.ngModel,function(oldValue,newValue){
                var isDate = angular.isDate(oldValue);
                ctrl.$setValidity('dateChk',isDate);
            });

        }
    }
}])


/**
 * Checks that the gambler is not trying to join a team he has already applied to/ already belongs to
 */
zenFootDirectives.directive('ligueCheck',[function(){
    return {
        require:'ngModel',
        link:function(scope,element,attrs,ctrl){
            scope.$watch('team.name',function(oldValue,newValue){
                ctrl.$setValidity('ligueChk',true);
                /*if(scope.team.name != '' && scope.statutTeamByName[scope.team.name]){
                    ctrl.$setValidity('ligueChk',false);
                }*/
            });

        }
    }
}])

/**
 * Checks that the name given to the event has not been given yet.
 */
zenFootDirectives.directive('eventNameCheck', ['AdminService',function(AdminService){
    return {
        require: 'ngModel',
        link:function(scope, element, attrs, ctrl){

            scope.$watch('eventName', function(oldValue, newValue){

                ctrl.$setValidity('eventNameCheck', true);
                if(oldValue!==newValue && AdminService.invalidEventName(scope.registeredEvents, scope.eventName)){
                    ctrl.$setValidity('eventNameCheck',false);
                }
            })
        }
    }
}])