(function () {
    'use strict';

    angular.module('zenFoot.app')
        .controller('AdminUsersController', ['$scope', 'User', '$timeout',
            function ($scope, User, $timeout) {

                $scope.allUsers = [];

                initData();

                function initData() {
                    User.query().$promise
                        .then(updateUsers);
                };

                $scope.searchNameCriteria = "";
                $scope.searchUsers = function(){
                    User.query({name: $scope.searchNameCriteria})
                        .$promise
                        .then(updateUsers)
                };

                function updateUsers(users){
                    $scope.allUsers = users;
                    $scope.setPagingData(users, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
                };

                //var parieurTemplate = {};
                //parieurTemplate['gambler'] = '<div class="ngCellText"><a href="#/bets?gamblerId={{row.entity.gamblerId}}">{{row.entity.prenom}} {{row.entity.nom}}</a></div>';
                //parieurTemplate['ligue'] = '<div class="ngCellText"><a href="#/ligueDetails?id={{row.entity.id}}">{{row.entity.name}}</a></div>';


                $scope.totalServerItems = 0;

                $scope.pagingOptions = {
                    pageSizes: [10, 20],
                    pageSize: 10,
                    currentPage: 1
                };

                //var displayName = {};
                //displayName['gambler'] = 'Parieur';
                //displayName['ligue'] = 'Ligue';
                //
                //var displayName2 = {};
                //displayName2['gambler'] = 'Points';
                //displayName2['ligue'] = 'Moyenne';
                //
                var columnDefs = [
                    {
                        displayName: 'Email',
                        field: 'email'
                    },
                    {
                        displayName: 'Nom',
                        field: 'lastname'
                    },
                    {
                        displayName: 'Prenom',
                        field: 'firstname'
                    },
                    {
                        displayName: 'Roles',
                        field: 'roles'
                    },
                    {
                        displayName: 'Actif',
                        field: 'isActive'
                    }
                ];

                //$scope.columnSelected = columnDef;


                $scope.gridOptions = {
                    data: 'pageData',
                    columnDefs: columnDefs,
                    enableRowSelection: true,
                    enablePaging: true,
                    showFooter: true,
                    totalServerItems: 'totalServerItems',
                    pagingOptions: $scope.pagingOptions,
                    enableSorting: true
                    //rowTemplate: '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="{\'zen-bold\':row.entity.focused}" class="ngCell {{col.cellClass}} {{col.colIndex()}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>'
                };

                $scope.setPagingData = function (data, page, pageSize) {
                    setTimeout(function () {
                        $scope.totalServerItems = data.length;
                        var pageData = data.slice((page - 1) * pageSize, page * pageSize);
                        $scope.pageData = pageData;
                        $scope.$apply();
                        // Styling the grid pager
                        $('.ngPagerControl .ngPagerButton').addClass('btn btn-default');
                        $('.ngPagerCurrent').addClass('form-control');
                    }, 100);
                };


                var watchAction = function () {
                    $scope.setPagingData($scope.allUsers, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
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

                //$scope.$watch('mode', function (newValue, oldValue) {
                //    if (newValue !== oldValue) {
                //        $scope.columnSelected = [
                //            {
                //                field: 'classement',
                //                displayName: '#',
                //                width: 30,
                //                headerClass: 'rankingHeader',
                //                cellClass: 'rankingCell'
                //            },
                //            {
                //                displayName: displayName[$scope.mode],
                //                cellTemplate: parieurTemplate[$scope.mode]
                //            },
                //            {
                //                field: 'points',
                //                displayName: displayName2[$scope.mode],
                //                width: 100
                //            }
                //        ];
                //
                //        initData();
                //    }
                //})


                ////si deja selectionné
                //if ($scope.selectedEvent != undefined) {
                //    initData();
                //}
                //$scope.$on('eventChanged', function (event, params) {
                //    initData();
                //})
            }]);
})();