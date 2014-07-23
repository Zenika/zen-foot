'use strict';

angular.module('zenFoot.app')
    .controller('IndexCtrl', function ($rootScope, $scope, $state, Session, User, Gambler, authService) {

        function onConnected(principal) {
            Session.user.connected = true;
            Session.user.email = principal.email;
            Session.user.nom = principal.nom;
            Session.user.prenom = principal.prenom;
            Session.user.roles = principal.roles;
        }


        $rootScope.user = Session.user;
        $scope.$on('AUTHENTICATED', function (event, principal) {
            onConnected(principal);
        });

        User.get({email: 'current' }).$promise
            .then(function (response) {
                return response.principal;
            })
            .then(onConnected)
            .catch(function () {
                Session.user.connected = false;
                delete Session.user.fullName;
                delete Session.user.email;
            });


        $scope.login = function () {
            $state.go('unconnected.login')
        };

        $scope.logout = authService.logout

        $scope.loggedIn = function () {
            return Session.user.connected;
        };


        $rootScope.isAdmin = function () {
            return _.contains($rootScope.user.roles, 'ADMIN');
        };

        $rootScope.isConnected = function () {
            return $rootScope.user.connected;
        };

        $scope.isActive = function (state) {
            return $state.includes(state);
        };

        $scope.hideNavBar = function () {
            return !$rootScope.user.connected;
        }

        $scope.getMenuItem2 = function(){
            return 'prono';
        }



    });
