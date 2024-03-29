'use strict';

angular.module('zenFoot.app').controller('ProfilCtrl', ['$scope', 'Pwd', '$timeout', '$q', 'Gambler','$rootScope', function ($scope, Pwd, $timeout, $q, Gambler,$rootScope) {

    //scope variables for gambler's first and family names

    $q.when(Gambler.get().$promise, function (gambler) {
        $scope.gambler = gambler;
        //tampon is used to know whether the user has already modified their name (used to disable or not the validation button)
        // TODO : improve validation using this variable
        $scope.tampon = angular.copy(gambler);
    })

    $scope.sendNames = function () {
        Gambler.update($scope.tampon,function(response){
            $scope.gambler = response[1];
            $scope.tampon = angular.copy($scope.gambler);
            var user = response[0];
            $rootScope.user.nom = user.nom;
            $rootScope.user.prenom = user.prenom;
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
        if(!$scope.gambler)return;
        return $scope.gambler.prenom !== $scope.tampon.prenom || $scope.gambler.nom !== $scope.tampon.nom;
    }

}])