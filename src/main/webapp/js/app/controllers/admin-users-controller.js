(function () {
    'use strict';

    angular.module('zenFoot.app')
        .controller('AdminUsersController', ['$scope', 'User', 'UserService', '$timeout', '$q',
            function ($scope, User, UserService, $timeout, $q) {

                $scope.allUsers = [];

                initData();

                function initData() {
                    User.query().$promise
                        .then(updateUsers);
                };

                $scope.searchNameCriteria = "";
                $scope.searchUsers = function () {
                    User.query({name: $scope.searchNameCriteria})
                        .$promise
                        .then(updateUsers);
                };

                function updateUsers(users) {
                    $scope.allUsers = users;
                    $scope.setPagingData(users, $scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
                };

                $scope.totalServerItems = 0;

                $scope.pagingOptions = {
                    pageSizes: [10, 20],
                    pageSize: 10,
                    currentPage: 1
                };

                var columnDefs = [
                    {
                        displayName: 'Email',
                        field: 'email'
                    },
                    {
                        displayName: 'Nom',
                        field: 'lastName'
                    },
                    {
                        displayName: 'Prenom',
                        field: 'firstName'
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
                     $timeout(function () {
                        $scope.totalServerItems = data.length;
                        var pageData = data.slice((page - 1) * pageSize, page * pageSize);
                        $scope.pageData = pageData;
                        //$scope.$apply();
                        // Styling the grid pager
                        $('.ngPagerControl .ngPagerButton').addClass('btn btn-default');
                        $('.ngPagerCurrent').addClass('form-control');
                    }, 500);
                    //setTimeout(function () {
                    //    $scope.totalServerItems = data.length;
                    //    var pageData = data.slice((page - 1) * pageSize, page * pageSize);
                    //    $scope.pageData = pageData;
                    //    //$scope.$apply();
                    //    // Styling the grid pager
                    //    $('.ngPagerControl .ngPagerButton').addClass('btn btn-default');
                    //    $('.ngPagerCurrent').addClass('form-control');
                    //}, 100);
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

                //$scope.deleteUsers = function () {
                //    var selectedItems = getSelectedItems($scope.gridOptions);
                //    if (selectedItems.length > 0) {
                //        var confirmation = confirm("Etes-vous sur de vouloir supprimer " + selectedItems.length + " utilisateurs?");
                //        if (confirmation) {
                //            UserService.deleteUsers(selectedItems)
                //                .then(function () {
                //                    $scope.searchUsers();
                //                    confirmMessage("Utilisateurs supprimés");
                //                }, function () {
                //                    errorMessage("Erreur dans la suppression de certains utilisateurs.");
                //                });
                //        }
                //    }
                //};

                $scope.deleteUsers = function(){
                    adminActionOnUsers("Supprimer", function(users){
                        return UserService.deleteUsers(users);
                    });
                };

                $scope.activateUsers = function(){
                    adminActionOnUsers("Activer", function(users){
                        return UserService.activateUsers(users);
                    });
                };

                $scope.resetPWDs = function () {
                    adminActionOnUsers("Reset PWDs", function(users){
                        return UserService.resetPWDs(users);
                    });
                };

                /**
                 * Ask confirmation for actionName. If confirmed, retrieve all selected users from the grid
                 * and perform action on it. Once action is resolved, display a success/error message
                 * @param actionName
                 * @param action
                 */
                function adminActionOnUsers(actionName, action){
                    var selectedItems = getSelectedItems($scope.gridOptions);
                    if (selectedItems.length > 0) {
                        var confirmation = confirm("Confirmer " + actionName + " pour " + selectedItems.length + " utilisateurs?");
                        if (confirmation) {
                            action(selectedItems)
                                .then(function () {
                                    $scope.searchUsers();
                                    confirmMessage(actionName + " pour " + selectedItems.length + " utilisateurs OK.");
                                }, function () {
                                    errorMessage("Erreur " + actionName + " pour certains utilisateurs.");
                                });
                        }
                    }
                }

                function getSelectedItems(gridOptions) {
                    return _.pluck(_.where(gridOptions.ngGrid.rowCache, {selected: true}), 'entity');
                }

                $scope.infoMessage = {
                    display: false,
                    message: "",
                    class: ""
                };

                function confirmMessage(msg) {
                    $scope.infoMessage.display = true;
                    $scope.infoMessage.message = msg;
                    $scope.infoMessage.class = "alert-success";
                }

                function errorMessage(msg) {
                    $scope.infoMessage.display = true;
                    $scope.infoMessage.message = msg;
                    $scope.infoMessage.class = "alert-danger";
                }

            }]);
})();