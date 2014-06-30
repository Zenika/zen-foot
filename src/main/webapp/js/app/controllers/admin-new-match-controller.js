'use strict';

angular.module('zenFoot.app')
    .controller('AdminFinalesCtrl', ['Pays', '$scope', 'betMatchService', 'Match', '$timeout', function (Pays, $scope, betMatchService, Match, $timeout) {
        Pays.getPays().then(function (response) {
            $scope.countries = response.data;
        })

        $scope.groups = betMatchService.group1().concat(betMatchService.group2());

        $scope.today = new Date();

        $scope.newMatch = function () {
            $scope.match = {team1: '', team2: '', score1: null, score2: null, date: null};
        }
        $scope.newMatch();

        $scope.format = 'dd/MM/yyyy';

        /**
         * TimePicker model is initialized to midnight. As match.date is initialized to null, the timepicker would otherwise
         * display the current time as default.We'd prefer to display midnight as default.
         * @type {Date}
         */

        $scope.open = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };

        $scope.toggleMin = function () {
            $scope.minDate = $scope.minDate ? null : new Date();
        };
        $scope.toggleMin();

        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };


        var checkDate = function (date) {
            if (date.getTimezoneOffset() == 120)return date;
            var decalage = (-1) * date.getTimezoneOffset() - 120;
            var newDate = angular.copy(date);
            newDate.setMinutes(newDate.getMinutes() + decalage);
            return newDate;
        }

        $scope.register = function (match) {
            var message = 'Voulez vous enregistrer le match suivant ? : ';
            message += '\n' + match.team1 + " - " + match.team2;
            message += '\n' + match.date.getDate() + '/' + (match.date.getMonth()+1) + '/' + match.date.getFullYear() + ' à ' + match.date.getHours() + ':' + match.date.getMinutes();
            var confirmation = confirm(message);
            if (confirmation) {
                match.date = checkDate(match.date);
                Match.save(match, function () {
                    $scope.newMatch();
                    $scope.registeredMatch = match;
                    $timeout(function () {
                        delete $scope.registeredMatch;
                    }, 8000);
                });
            }
        }

    }])