(function () {
    'use strict';
    angular.module('zenFoot.directives')
        .directive(['$http', function ($http) {
            return {
                restrict: 'E',
                scope: {
                    liguePropositions: '='
                },
                link: function (scope, element, attrs) {
                    scope.accept = function () {
                        scope.liguePropositions.splice($index, 1);
                        $scope.ligue.accepted.push(applicant);
                        Events.updateLigue({id : $scope.selectedEvent.id, idLigue : $stateParams.id}, $scope.ligue);
                    };
                    scope.refuse = function () {
                        scope.liguePropositions.splice($index, 1);
                    };
                }
            };
        }]);
})();