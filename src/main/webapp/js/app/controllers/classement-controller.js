/**
 * Created by raphael on 20/05/14.
 */
var zenfootModule = angular.module('zenFoot.app');

zenfootModule.controller('ClassementCtrl', ['$scope', 'GamblerService', '$q', 'Gambler', 'Team', function ($scope, GamblerService, $q, Gambler, Team) {

    $scope.gambler = Gambler.get();

    $scope.teams = Team.getAll()

    /**
     * Retrieves the ranking from the server, sorts it, affects the ranking to each player, and affects the resulting list to the scope
     */
    $scope.classementFunc = function (ranking) {
        var promise = ranking.$promise;
        /*     var ranking = mock;
         var promise = $q.when(ranking);
         */

    $scope.classement = promise.then(function (ranking) {
        var rankingSorted = _.sortBy(ranking, function (peopleRanking) {
            return -peopleRanking.points;
        })

        for (var x in rankingSorted) {

            rankingSorted[x].classement = parseInt(x) + 1;
        }
        $scope.classement = rankingSorted;
        return rankingSorted;

        })
        .then(function (rankingSorted) {
            $scope.setPagingData(rankingSorted, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
            return rankingSorted;

        })
    }

    $scope.classementFunc(GamblerService.getAll());


    /**
     * The ranking is obtained, once the list is ordered by descending points, by the index of the entity
     * @param row
     * @returns {*}
     */
    $scope.getClassement = function (row) {
        return row + 1;
    }

    var classementTemplate = '<div>{{getClassement(row.rowIndex)}}</div>'
    var parieurTemplate = '<div class="ngCellText">{{row.entity.prenom}} {{row.entity.nom}}</div>'


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
                width: 100,
            }
        ],
        enableRowSelection: false,
        enablePaging: true,
        showFooter: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        enableSorting: false,
        rowTemplate: '<div ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="{\'zen-bold\':row.entity.focused}" class="ngCell {{col.cellClass}} {{col.colIndex()}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>'
    }


    $scope.setPagingData = function (data, page, pageSize) {
        setTimeout(function () {

            $scope.totalServerItems = data.length;
            var pageData = data.slice((page - 1) * pageSize, page * pageSize)
            $scope.pageData = pageData;
            var bool = scopeApplied = false;
            $scope.$apply()
            // Styling the grid pager
            $('.ngPagerControl .ngPagerButton').addClass('btn btn-default');
            $('.ngPagerCurrent').addClass('form-control');
        }, 100)

    }


    var watchAction = function () {
        $scope.setPagingData($scope.classement, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
    }

    $scope.$watch('pagingOptions.currentPage', function (newVal, oldVal) {
        if (newVal != oldVal) {
            watchAction()
        }
    })

    $scope.$watch('pagingOptions.pageSize', function (newVal, oldVal) {
        if (newVal != oldVal) {
            watchAction()
        }
    })

    /*
     called when the gambler would like to display the result of a specific gambler. The page of the tab
     is changed to that where the gambler is listed.
     */

    $scope.changePage = function (gambler) {
        if(gambler) {
            if ($scope.focusedGambler) {
                delete $scope.focusedGambler.focused;
            }
            $scope.focusedGambler = gambler;
            gambler.focused = true;
            var roundedPage = gambler.classement / $scope.pagingOptions.pageSize;
            var page = Math.ceil(roundedPage);
            $scope.pagingOptions.currentPage = page;
        }

    }


    $scope.findClassement = function (gambler) {
        var classement = $scope.classement;

        var result = _.find($scope.classement, function (item) {
            return item.id == gambler.id;
        });
        if (result)return result.classement;

    }

    /**
     * Finds the current gambler in the ranking list. Necessary because ranking is calculated client side based on the resulting sorted list
     */
    $scope.findGambler = function () {
        if ($scope.gambler) {
            var id = $scope.gambler.id;

            return _.find($scope.classement, function (item) {
                return item.id == id;
            })
        }
    }

}])
