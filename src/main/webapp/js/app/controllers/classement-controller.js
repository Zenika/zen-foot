/**
 * Created by raphael on 20/05/14.
 */
var zenfootModule = angular.module('zenFoot.app');

zenfootModule.controller('ClassementCtrl', ['$scope', 'GamblerService', '$q', 'Gambler', function ($scope, GamblerService, $q, Gambler) {

    $scope.gambler = Gambler.get();

    var mock = [
        {id: 1, email: "raphael.martignoni@zenika.com", nom: "Martignoni", prenom: "Raphaël", points: 40},
        {id: 2, email: "bertrand.bouchard@zenika.com", nom: "Bouchard", prenom: "Bertrand", points: 140},
        {id: 3, email: "jean-claude.duss@zenika.com", nom: "Duss", prenom: "Jean-Claude", points: 44},
        {id: 4, email: "richard.virenque@zenika.com", nom: "Virenque", prenom: "Richard", points: 45},
        {id: 5, email: "olivier.martinez@zenika.com", nom: "Martinez", prenom: "Olivier", points: 50},
        {id: 6, email: "mira.sorvino@zenika.com", nom: "Sorvino", prenom: "Mira", points: 90},
        {id: 7, email: "kate.winslet@zenika.com", nom: "Winslet", prenom: "Kate", points: 60},
        {id: 8, email: "leonardo.dicaprio@zenika.com", nom: "Di-Caprio", prenom: "Leonardo", points: 52},
        {id: 9, email: "russel.crowe@zenika.com", nom: "Crowe", prenom: "Russell", points: 49},
        {id: 10, email: "andy.mac-dowell@zenika.com", nom: "Mac-Dowell", prenom: "Andy", points: 83},
        {id: 11, email: "bart.simson@zenika.com", nom: "Murray", prenom: "Bill", points: 51},
        {id: 12, email: "harold.ramis@zenika.com", nom: "Ramis", prenom: "Harold", points: 69},
        {id: 13, email: "sophie.marceau@zenika.com", nom: "Marceau", prenom: "Sophie", points: 78}
    ];

    /**
     * Retrieves the ranking from the server, sorts it, affects the ranking to each player, and affects the resulting list to the scope
     */
    var classementFunc = function () {
        var ranking = GamblerService.getAll();
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

    classementFunc();


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
        enableSorting:false,
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
        if ($scope.focusedGambler) {
            delete $scope.focusedGambler.focused;
        }
        $scope.focusedGambler = gambler;
        gambler.focused = true;
        var roundedPage = gambler.classement / $scope.pagingOptions.pageSize;
        var page = Math.ceil(roundedPage);
        $scope.pagingOptions.currentPage = page;


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
