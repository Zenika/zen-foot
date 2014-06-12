'use strict';

angular.module('zenFoot.app')
    .controller("subscribeCtrl", ['$scope', '$resource', '$http', '$rootScope', '$state', '$modal', 'Team',
        function ($scope, $resource, $http, $rootScope, $state, $modal, Team) {
            $scope.subscriber = {teams: [
                {name: "", isNew: false}
            ]};
            $rootScope.subscriber = {};
            $scope.existingTeams = Team.getAll();

            var checkTeams = function () {
                for (var x in $scope.subscriber.teams) {
                    if ($scope.subscriber.teams[x].name.trim() == "") {
                        $scope.subscriber.teams.splice(x)
                    }
                }
            };

            var subscribe = function () {
                checkTeams();
                var Subscription = $resource('/api/performSubscription');
                Subscription.save({user: $scope.subscriber, teams: $scope.subscriber.teams}, function () {
                    $state.go('loginState', {subscriptionSuccess: true});
                    $rootScope.subscriber = $scope.subscriber;
                }, function (putResponseHeaders) {
                	if (putResponseHeaders.status == 400) {
                		$scope.subscriptionForm.$setPristine();
                		$scope.subscriber.subscriptionErrorAlreadyUsedEmail = true;
                        $rootScope.subscriber = $scope.subscriber;
                	}
                });
            };

            var subscribeGroups = function () {
                var modalInstance = $modal.open({backdrop: 'static', scope: $scope, templateUrl: 'view/modal-sub.html'})
                $scope.modalInstance = modalInstance

                modalInstance.result.then(function (response) {
                    if (response == true) {
                        subscribe()
                    }
                })
            };


            $scope.pushTeam = function () {
                $scope.subscriber.teams.push({name: ''})
            };


            $scope.valider = function () {
                if ($scope.subscriptionForm.$invalid) {
                    return;
                }
                if (Team.hasNewGroup($scope.subscriber.teams)) {
                    subscribeGroups()
                }
                else {
                    subscribe()
                }

            };

            $scope.ok = function () {
                $scope.modalInstance.close(true);
            };

            $scope.cancel = function () {
                $scope.modalInstance.dismiss();
            };


        }])

    .controller("confirmSubscriptionCtrl", ['$timeout', '$stateParams', '$resource',
        function ($timeout, $stateParams, $resource) {
            var confirmSubscription = $resource('/api/confirmSubscription', {email: $stateParams.id});
            confirmSubscription.get($stateParams.id, function (data) {
                alert(data == "false");
            });
        }]);
