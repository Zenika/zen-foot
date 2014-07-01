'use strict';

angular.module('zenFoot.app')
    .controller('ClassementCtrl', ['$scope', 'GamblerService', 'RankingService', '$q', 'Gambler', 'Team', '$timeout',
        function ($scope, GamblerService, RankingService, $q, Gambler, Team, $timeout) {

            $scope.gambler = Gambler.get();

            $scope.teams = Team.getAll();


            /**
             * Retrieves the ranking from the server, sorts it, affects the ranking to each player, and affects the resulting list to the scope
             */
            $scope.classementFunc = function (ranking) {
                var promise = ranking.$promise;
                /*     var ranking = mock;
                 var promise = $q.when(ranking);
                 */

                $scope.classement = promise.then(function (ranking) {
                    /*var rankingSorted = _.sortBy(ranking, function (peopleRanking) {
                     return -peopleRanking.points;
                     });*/
                    var equality = false;
                    for (var i = 0; i < ranking.length; i++) {
                        var ii = i - 1;
                        if (i > 0 && (ranking[ii].points != ranking[i].points)) {
                            ranking[i].classement = i + 1;
                            equality = false;
                        } else {
                            //initiating for first in ranking
                            if(i==0){
                                ranking[0].classement = 1;
                            }
                            else{
                                ranking[i].classement = ranking[ii].classement;
                                equality = true;
                            }
                        }
                        ranking[i].equality = equality;
                    }

                    $scope.classement = ranking;
                    return ranking;

                })
                    .then(function (ranking) {
                        $scope.setPagingData(ranking, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
                        return ranking;

                    })
                    .then(function (ranking) {
                        $timeout(function () {
                            $scope.personnalRanking = $scope.findPersonnalRanking($scope.gambler)

                        }, 100)
                    })
            };

            $scope.classementFunc(RankingService.getAll());


            /**
             * The ranking is obtained, once the list is ordered by descending points, by the index of the entity
             * @param row
             * @returns {*}
             */
            $scope.getClassement = function (row) {
                return row + 1;
            };

            var classementTemplate = '<div>{{getClassement(row.rowIndex)}}</div>';
            var parieurTemplate = '<div class="ngCellText""><a ui-sref="betsState({gamblerId:\'{{row.entity.gamblerId}}\'})">{{row.entity.prenom}} {{row.entity.nom}}</a></div>';


            $scope.totalServerItems = 0;

            $scope.pagingOptions = {
                pageSizes: [10, 20],
                pageSize: 10,
                currentPage: 1
            };

            $scope.gridOptions = {
                data: 'pageData',
                columnDefs: [
                    {
                        field: 'classement',
                        displayName: '#',
                        width: 30,
                        headerClass: 'rankingHeader',
                        cellClass: 'rankingCell'
                    },
                    {
                        displayName: 'Parieur',
                        cellTemplate: parieurTemplate
                    },
                    {
                        field: 'points',
                        displayName: 'Points',
                        width: 100
                    }
                ],
                enableRowSelection: false,
                enablePaging: true,
                showFooter: true,
                totalServerItems: 'totalServerItems',
                pagingOptions: $scope.pagingOptions,
                enableSorting: false,
                rowTemplate: '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="{\'zen-bold\':row.entity.focused}" class="ngCell {{col.cellClass}} {{col.colIndex()}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>'
            };


            $scope.setPagingData = function (data, page, pageSize) {
                setTimeout(function () {

                    $scope.totalServerItems = data.length;
                    var pageData = data.slice((page - 1) * pageSize, page * pageSize);
                    $scope.pageData = pageData;
                    var bool = false;
                    var scopeApplied = false;
                    $scope.$apply();
                    // Styling the grid pager
                    $('.ngPagerControl .ngPagerButton').addClass('btn btn-default');
                    $('.ngPagerCurrent').addClass('form-control');
                }, 100)

            };


            var watchAction = function () {
                $scope.setPagingData($scope.classement, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
            };

            $scope.$watch('pagingOptions.currentPage', function (newVal, oldVal) {
                if (newVal != oldVal) {
                    watchAction()
                }
            });

            $scope.$watch('pagingOptions.pageSize', function (newVal, oldVal) {
                if (newVal != oldVal) {
                    watchAction()
                }
            });

            /*
             called when the gambler would like to display the result of a specific gambler. The page of the tab
             is changed to that where the gambler is listed.
             */

            $scope.changePage = function (gamblerRanking) {
                if (gamblerRanking) {

                    if ($scope.focusedGambler) {
                        delete $scope.focusedGambler.focused;
                    }
                    $scope.focusedGambler = gamblerRanking;
                    gamblerRanking.focused = true;
                    var roundedPage = gamblerRanking.classement / $scope.pagingOptions.pageSize;
                    var page = Math.ceil(roundedPage);

                    $scope.pagingOptions.currentPage = page;
                }

            };

            $scope.changeToGambler = function (ranking) {
                $scope.changePage(ranking)
            }


            $scope.findClassement = function (gambler) {
                var ranking = $scope.findPersonnalRanking(gambler);
                return ranking.classement;

            };

            $scope.findPersonnalRanking = function (gambler) {
                var classement = $scope.classement;

                var result = _.find(classement, function (item) {
                    return item.gamblerId == gambler.id;
                });
                if (result)return result;
            }

            /**
             * Finds the ranking object of the current gambler in the ranking list. Necessary because ranking is given client side, depending on the position of the player in the array
             */
            $scope.findGambler = function () {
                if ($scope.gambler) {
                    var id = $scope.gambler.id;

                    return _.find($scope.classement, function (item) {
                        return item.id == id;
                    })
                }
            };

        }]);
