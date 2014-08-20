angular.module('zenFoot.app')
    .controller('ResetPWDCtrl', ['$scope', 'ResetPWD', '$stateParams', '$timeout', '$rootScope',function ($scope, ResetPWD, $stateParams, $timeout, $rootScope) {

        $scope.resetPWD = function(){
            ResetPWD.save({pwdLinkId: $stateParams.resetid, newPWD: $scope.password},
                function(response){
                    $scope.pwdChangedMessage = true;
                    $scope.pwdChanged = true;

                    $timeout(function(){

                        $rootScope.shakeLogo = true;

                        $timeout(function(){
                            $rootScope.shakeLogo = false;
                        },2000);
                    }, 1500)
                }
                ,
                function(response){
                    if(response.status == 404){
                        $scope.pwdNotChanged = true;
                        $timeout(function(){
                            delete $scope.pwdNotChanged
                        },5000);
                    }
            });
        }

    }]);