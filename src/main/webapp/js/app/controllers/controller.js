/**
 *
 */

'use strict';
var controllers = angular.module('zenFoot.controllers', ['ngResource']);

controllers.controller('HelloCtrl', ['$scope', '$resource',
    function ($scope, $resource) {

        $scope.sentence = $resource('/api/:param', {param: 'coucou'}).get();

    } ]);

/**
 * This controller sends the login request to restx and spreads an AUTHENTICATED event to scopes.
 */
controllers.controller('LoginCtrl', function ($scope, $rootScope, $http, $location) {
    $scope.login = { };

    $scope.submit = function () {
        $http.post('/api/sessions',
            {principal: {name: $scope.login.email, passwordHash: $scope.login.password}},
            {withCredentials: true}
        )
            .success(function (data, status, headers, config) {
                console.log('authenticated', data, status);
                $rootScope.$broadcast('AUTHENTICATED', data.principal);
                $location.path('/index');
            }).error(function (data, status, headers, config) {
                console.log('error', data, status);
                alert("Authentication error, please try again.");
            });
    }
});

controllers.controller('MatchCtrl', ['$scope', 'matchService', function ($scope, matchService) {
    $scope.matchs = matchService.getAll();


}]);

controllers.controller('MatchMockCtrl', ['$scope', function ($scope) {
    $scope.groupeA = [
        {"id": 0, "date": "2014-06-12T22:00:00.000+02:00", "participant1": {"pays": "Croatie"}, "participant2": {"pays": "Bresil"}, "outcome": null},
        {"id": 1, "date": "2014-06-13T18:00:00.000+02:00", "participant1": {"pays": "Cameroun"}, "participant2": {"pays": "Mexique"}, "outcome": null},
        {"id": 15, "date": "2014-06-17T21:00:00.000+02:00", "participant1": {"pays": "Bresil"}, "participant2": {"pays": "Mexique"}, "outcome": null},
        {
            "id": 19,
            "date": "2014-06-18T00:00:00.000+02:00",
            "participant1": {
                "pays": "Cameroun"
            },
            "participant2": {
                "pays": "Croatie"
            },
            "outcome": null
        }
    ];
    $scope.groupeB =
        [
            {"id": 2, "date": "2014-06-13T21:00:00.000+02:00", "participant1": {"pays": "Espagne"}, "participant2": {"pays": "Pays-Bas"}, "outcome": null},
            {"id": 3, "date": "2014-06-13T00:00:00.000+02:00", "participant1": {"pays": "Chili"}, "participant2": {"pays": "Australie"}, "outcome": null},
            {"id": 17, "date": "2014-06-18T18:00:00.000+02:00", "participant1": {"pays": "Australie"}, "participant2": {"pays": "Pays-Bas"}, "outcome": null}

        ];

    $scope.groupeC = [
        {"id": 4, "date": "2014-06-14T18:00:00.000+02:00", "participant1": {"pays": "Colombie"}, "participant2": {"pays": "Grece"}, "outcome": null},
        {"id": 7, "date": "2014-06-14T03:00:00.000+02:00", "participant1": {"pays": "Côte d'Ivoire"}, "participant2": {"pays": "Japon"}, "outcome": null}
    ];

    $scope.groupeD = [
        {
            "id": 5,
            "date": "2014-06-14T21:00:00.000+02:00",
            "participant1": {
                "pays": "Uruguay"
            },
            "participant2": {
                "pays": "Costa Rica"
            },
            "outcome": null
        },
        {
            "id": 6,
            "date": "2014-06-14T00:00:00.000+02:00",
            "participant1": {
                "pays": "Angleterre"
            },
            "participant2": {
                "pays": "Italie"
            },
            "outcome": null
        }
    ];

    $scope.groupeE = [
        {
            "id": 8,
            "date": "2014-06-15T18:00:00.000+02:00",
            "participant1": {
                "pays": "Suisse"
            },
            "participant2": {
                "pays": "Equateur"
            },
            "outcome": null
        },
        {
            "id": 9,
            "date": "2014-06-15T21:00:00.000+02:00",
            "participant1": {
                "pays": "France"
            },
            "participant2": {
                "pays": "Honduras"
            },
            "outcome": null
        }
    ];

    $scope.groupeF = [

        {
            "id": 10,
            "date": "2014-06-15T00:00:00.000+02:00",
            "participant1":
            {
                "pays": "Argentine"
            },
            "participant2":
            {
                "pays": "Bosnie-et-Herzegovine"
            },
            "outcome": null
        },
        {
            "id": 12,
            "date": "2014-06-16T21:00:00.000+02:00",
            "participant1":
            {
                "pays": "Iran"
            },
            "participant2":
            {
                "pays": "Nigéria"
            },
            "outcome": null
        }

    ];

    $scope.groupeG = [
        {
            "id": 13,
            "date": "2014-06-16T00:00:00.000+02:00",
            "participant1":
            {
                "pays": "Ghana"
            },
            "participant2":
            {
                "pays": "USA"
            },
            "outcome": null
        }
    ];

    $scope.groupeH=[
        {
            "id": 14,
            "date": "2014-06-17T18:00:00.000+02:00",
            "participant1":
            {
                "pays": "Belgique"
            },
            "participant2":
            {
                "pays": "Algérie"
            },
            "outcome": null
        },
        {
            "id": 16,
            "date": "2014-06-17T00:00:00.000+02:00",
            "participant1":
            {
                "pays": "Russie"
            },
            "participant2":
            {
                "pays": "République de Corée"
            },
            "outcome": null
        }
    ];
}]);


controllers.controller('navBarController', function ($scope, $location, isActiveService) {
    $scope.isActive = function (path) {
        isActiveService.isActive(path);
    }

});

