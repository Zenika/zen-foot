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
                for (var i=0;i<$scope.subscriber.teams.length;i++) {
                    if ($scope.subscriber.teams[i].name.trim() == "") {
                        $scope.subscriber.teams.splice(i)
                    }
                }
            };

            var subscribe = function () {
                checkTeams();
                var Subscription = $resource('/api/performSubscription');
                Subscription.save({user: $scope.subscriber, teams: $scope.subscriber.teams}, function () {
                    $state.go('loginState', {subscriptionSuccess: true});
                    $rootScope.subscriber = $scope.subscriber;
                }, function (postResponse) {
                	$scope.subscriptionForm.$setPristine();
                	
                	if (postResponse.status == 400 && postResponse.data.hasOwnProperty('errorCode') && postResponse.data.errorCode == "SUBSCRIPTION_ERROR_ALREADY_USED_EMAIL") {
                		$scope.subscriber.subscriptionError = false;
                		$scope.subscriber.subscriptionErrorAlreadyUsedEmail = true;
                	} else {
                		$scope.subscriber.subscriptionError = true;
                		$scope.subscriber.subscriptionErrorAlreadyUsedEmail = false;
                	}
                	
                	$rootScope.subscriber = $scope.subscriber;
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
