'use strict';

/**
 * This controller sends the login request to restx and spreads an AUTHENTICATED event to scopes.
 */
angular.module('zenFoot.app')
    .controller('LoginCtrl', ['$scope', '$rootScope', '$http', '$location',
        function ($scope, $rootScope, $http, $location) {
            $scope.login = { };

            if ($rootScope.subscriber != null) {
                $scope.login = $rootScope.subscriber.login;
            }

            $scope.submit = function () {
                //This method is called by autofill directive, and form validation could not be tested before
                //(web browser does not update model values when it auto fill a form)
                if ($scope.loginForm.$invalid) {
                    return;
                }
                $http.post('/api/sessions',
                    {principal: {name: $scope.login.email, passwordHash: $scope.login.password}},
                    {withCredentials: true}
                )
                    .success(function (data, status, headers, config) {
                        //console.log('authenticated', data, status);
                        $rootScope.$broadcast('AUTHENTICATED', data.principal);
                        //Submit hidden form with classic HTTP POST to enabled password recording in browser
                        angular.element("#postLoginForm").submit();
                    }).error(function (data, status, headers, config) {
                        //console.log('error', data, status);
                        alert("Authentication error, please try again.");
                    });
            };

            $scope.subscribe = function () {
                $location.path('/subscribe');
            };
        }]);
