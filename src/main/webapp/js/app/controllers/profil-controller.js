'use strict';

angular.module('zenFoot.app').controller('ProfilCtrl', ['$scope', 'Pwd', '$timeout', '$q', 'User','$rootScope','Session', function ($scope, Pwd, $timeout, $q, User,$rootScope, Session) {

    $scope.user = $rootScope.user;
    $scope.modifiedUser = angular.copy($rootScope.user);

    $scope.modifyUser = function () {
        User.update($scope.modifiedUser,function(response){
            $rootScope.user.firstName = response.firstName;
            $rootScope.user.lastName = response.lastName;
            $scope.modifiedUser = angular.copy($scope.user);
            $scope.messageName = 'Vos données personnelles ont été modifiées';
            $timeout(function(){
                delete $scope.messageName;
            },5000);
        })
    }

    // scope variables for password change :

    $scope.passwordCouple = [];

    $scope.message = '';

    $scope.sendPW = function () {
        Pwd.save($scope.passwordCouple,
            function (response) {
                $scope.response = 'OK';
                $scope.message = 'Votre mot de passe a été réinitialisé !';
                $timeout(function () {
                    delete $scope.message;
                    delete $scope.response;
                }, 5000);
            },
            function (response) {
                if (response.status === 400 && response.data.hasOwnProperty('errorCode') && response.data.errorCode === 'WRONG_PWD') {
                    $scope.response = 'WRONG';
                    $scope.message = 'Mauvais mot de passe !';
                    $timeout(function () {
                        delete $scope.message;
                        delete $scope.response;
                    }, 5000);
                }
                else {
                    $scope.response = 'ERROR';
                    $scope.message = 'Désolé, une erreur est survenue...';
                    $timeout(function () {
                        delete $scope.message;
                        delete $scope.response;
                    }, 5000);
                }
            }
        );

    }

    $scope.resetPasswordForm = function () {
        $scope.confirmPW = null;
        $scope.passwordCouple[0] = null;
        $scope.passwordCouple[1] = null;
        $scope.editPassForm.$setPristine();
    }

    $scope.isOK = function () {
        return 'OK' === $scope.response;
    }

    $scope.isWrong = function () {
        return 'WRONG' === $scope.response;
    }

    $scope.isError = function () {
        return 'ERROR' == $scope.response;
    }

    $scope.canModifyNames=function(){
        if(!$scope.user)return;
        return $scope.user.firstName !== $scope.modifiedUser.firstName || $scope.user.lastName !== $scope.modifiedUser.lastName;
    }

}])