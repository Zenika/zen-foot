/**
 * 
 */

'use strict';
var controllers = angular.module('controllers', ['ngResource']);

controllers.controller('SimpleController', [ '$scope', 'HelloNHour',
		'updateSentenceService', function($scope, HelloNHour) {
			$scope.sentence = HelloNHour.update({
				who : $scope.nom
			});
			$scope.$watch('nom', function(newValue) {
				$scope.sentence = HelloNHour.update({
					who : newValue
				});
			});

		} ]);
controllers.controller('LoginCtrl', function ($scope, $rootScope, $http, $location, md5) {
    $scope.login = { };

    $scope.submit = function() {
        $http.post('/api/sessions',
            {principal: {name: $scope.login.email, passwordHash: $scope.login.password}},
            {withCredentials: true}
        )
            .success(function(data, status, headers, config) {
                console.log('authenticated', data, status);
                $rootScope.$broadcast('AUTHENTICATED', data.principal);
                $location.path('/index');
            }).error(function(data, status, headers, config) {
                console.log('error', data, status);
                alert("Authentication error, please try again.");
            });
    }
});


controllers.controller('UserCtrl', function ($rootScope, $scope, $location, Session, User) {
    function onConnected(principal) {
        Session.user.connected = true;
        Session.user.email = principal.email;
        Session.user.fullName = principal.fullName;
    }

    $scope.user = Session.user;
    $scope.$on('AUTHENTICATED', function(event, principal) {
        onConnected(principal);
    })

    User.get({email: 'current' }).$promise
        .then(onConnected)
        .catch(function() {
            Session.user.connected = false;
            delete Session.user.fullName;
            delete Session.user.email;
        });

    $scope.login = function() {
        $location.path('/login');
    }

    $scope.logout = function() {
        Session.delete(function() { location.reload() });
    }
});

controllers.controller('navBarController', function($scope, $location,
		isActiveService) {
	$scope.isActive = function(path) {
		isActiveService.isActive(path);
	}

});

controllers.controller('logOutController', function() {

});