'use strict';

angular.module('zenFoot.app')
    .controller('ClassementCtrl', ['$scope', 'RankingService', '$q', 'Gambler', 'TeamService', '$timeout',
        function ($scope, RankingService, $q, Gambler, TeamService, $timeout) {

            $scope.modes = {gambler: 'ligue', ligue: 'gambler'};

            $scope.switchMode = function () {
                $scope.mode = $scope.modes[$scope.mode];
            }

            //gambler or ligue
            $scope.mode = 'gambler';

            var modeInFrench = function (mode) {
                if (mode === 'ligue') {
                    return 'ligue';
                }
                else if (mode === 'gambler') {
                    return 'parieur';
                }
            }

            $scope.gamblerMode = function(){
                return $scope.mode === 'gambler';
            }

            $scope.ligueMode = function(){
                return $scope.mode === 'ligue';
            }

            $scope.switchModeDisplay = function(){
                return modeInFrench($scope.modes[$scope.mode]);
            }

            $scope.gambler = Gambler.get();

            $scope.teams = TeamService.getAll();

            $scope.serverRanking = {};
            $scope.serverRanking['gambler'] = function () {
                return RankingService.getAll().$promise;
            }
            $scope.serverRanking['ligue'] = function () {
                return [
                    {id: 6632254138744832, nom: 'zenika', points: '20'}
                ]
            }

            /**
             * Retrieves the ranking from the server, sorts it, affects the ranking to each player, and affects the resulting list to the scope
             */
            var initData = function () {
                $q.when($scope.serverRanking[$scope.mode]()).then(function (ranking) {

                        // initializing for first gambler :
                        if (ranking.length > 0) {
                            ranking[0].classement = 1;
                            ranking[0].index = 1;
                            ranking.equality = false;
                        }

                        if (ranking.length > 1 && (ranking[0].points == ranking[1].points)) {
                            ranking[0].equality = true;
                        }

                        // general algorithm
                        var equality = false;
                        var classement;
                        for (var i = 1; i < ranking.length; i++) {
                            var ii = i - 1;
                            if (ranking[ii].points != ranking[i].points) {
                                classement = i + 1;
                                equality = false;
                            } else {
                                classement = ranking[ii].classement;
                                equality = true;
                            }
                            ranking[i].equality = equality;
                            ranking[i].index = i + 1;
                            ranking[i].classement = classement;
                        }
                        return ranking;
                    }
                )
                    .then(function (ranking) {
                        $scope.setPagingData(ranking, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
                        $scope.classement = ranking;
                        return ranking;

                    })
                    .then(function (ranking) {
                        $timeout(function () {
                            $scope.personnalRanking = $scope.findPersonnalRanking($scope.gambler)

                        }, 100)
                    })
            }

            initData();

            /**
             * The ranking is obtained, once the list is ordered by descending points, by the index of the entity
             * @param row
             * @returns {*}
             */
            $scope.getClassement = function (row) {
                return row + 1;
            };

            var parieurTemplate = {};
            parieurTemplate['gambler'] = '<div class="ngCellText"><a href="#/bets?gamblerId={{row.entity.gamblerId}}">{{row.entity.prenom}} {{row.entity.nom}}</a></div>';
            parieurTemplate['ligue'] = '<div class="ngCellText"><a href="#/ligueDetails?id={{row.entity.id}}">{{row.entity.nom}}</a></div>';


            $scope.totalServerItems = 0;

            $scope.pagingOptions = {
                pageSizes: [10, 20],
                pageSize: 10,
                currentPage: 1
            };

            var displayName = {};
            displayName['gambler'] = 'Parieur';
            displayName['ligue'] = 'Ligue';

            var displayName2 = {};
            displayName2['gambler'] = 'Points';
            displayName2['ligue'] = 'Moyenne';

            var columnDef = [
                {
                    field: 'classement',
                    displayName: '#',
                    width: 30,
                    headerClass: 'rankingHeader',
                    cellClass: 'rankingCell'
                },
                {
                    displayName: displayName[$scope.mode],
                    cellTemplate: parieurTemplate[$scope.mode]
                },
                {
                    field: 'points',
                    displayName: displayName2[$scope.mode],
                    width: 100
                }
            ];

            $scope.columnSelected = columnDef;


            var initGridOptions = function () {

                $scope.gridOptions = {
                    data: 'pageData',
                    columnDefs: 'columnSelected',
                    enableRowSelection: false,
                    enablePaging: true,
                    showFooter: true,
                    totalServerItems: 'totalServerItems',
                    pagingOptions: $scope.pagingOptions,
                    enableSorting: false,
                    rowTemplate: '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="{\'zen-bold\':row.entity.focused}" class="ngCell {{col.cellClass}} {{col.colIndex()}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>'
                };
            }

            initGridOptions();

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
                    var roundedPage = gamblerRanking.index / $scope.pagingOptions.pageSize;
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

            $scope.$watch('mode', function (newValue, oldValue) {
                if (newValue !== oldValue) {
                    $scope.columnSelected = [
                        {
                            field: 'classement',
                            displayName: '#',
                            width: 30,
                            headerClass: 'rankingHeader',
                            cellClass: 'rankingCell'
                        },
                        {
                            displayName: displayName[$scope.mode],
                            cellTemplate: parieurTemplate[$scope.mode]
                        },
                        {
                            field: 'points',
                            displayName: displayName2[$scope.mode],
                            width: 100
                        }
                    ];

                    initData();
                }
            })

        }]);
