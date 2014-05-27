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

controllers.controller('MatchCtrl', ['$scope', 'betMatchService', 'postBetService', '$rootScope', '$q', 'displayService', '$rootScope','Gambler', function ($scope, betMatchService, postBetService, $rootScope, $q, displayService, Match,Gambler) {
    //$scope.matchsBets = betMatchService.getAll();
$scope.matchsBets=[{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4538783999459328,"won":false},"match":{"id":4538783999459328,"date":"2014-06-22T00:00:00.000+02:00","participant1":{"pays":"USA","groupe":"G"},"participant2":{"pays":"Portugal","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4573968371548160,"won":false},"match":{"id":4573968371548160,"date":"2014-06-17T21:00:00.000+02:00","participant1":{"pays":"Bresil","groupe":"A"},"participant2":{"pays":"Mexique","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4609152743636992,"won":false},"match":{"id":4609152743636992,"date":"2014-06-26T22:00:00.000+02:00","participant1":{"pays":"Algérie","groupe":"H"},"participant2":{"pays":"Russie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4644337115725824,"won":false},"match":{"id":4644337115725824,"date":"2014-06-14T03:00:00.000+02:00","participant1":{"pays":"Côte d'Ivoire","groupe":"C"},"participant2":{"pays":"Japon","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4679521487814656,"won":false},"match":{"id":4679521487814656,"date":"2014-06-24T22:00:00.000+02:00","participant1":{"pays":"Grece","groupe":"C"},"participant2":{"pays":"Côte d'Ivoire","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4714705859903488,"won":false},"match":{"id":4714705859903488,"date":"2014-06-20T18:00:00.000+02:00","participant1":{"pays":"Italie","groupe":"D"},"participant2":{"pays":"Costa Rica","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4785074604081152,"won":false},"match":{"id":4785074604081152,"date":"2014-06-13T00:00:00.000+02:00","participant1":{"pays":"Chili","groupe":"B"},"participant2":{"pays":"Australie","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4820258976169984,"won":false},"match":{"id":4820258976169984,"date":"2014-06-23T22:00:00.000+02:00","participant1":{"pays":"Croatie","groupe":"A"},"participant2":{"pays":"Mexique","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4855443348258816,"won":false},"match":{"id":4855443348258816,"date":"2014-06-18T00:00:00.000+02:00","participant1":{"pays":"Cameroun","groupe":"A"},"participant2":{"pays":"Croatie","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4925812092436480,"won":false},"match":{"id":4925812092436480,"date":"2014-06-16T18:00:00.000+02:00","participant1":{"pays":"Allemagne","groupe":"G"},"participant2":{"pays":"Portugal","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4960996464525312,"won":false},"match":{"id":4960996464525312,"date":"2014-06-25T22:00:00.000+02:00","participant1":{"pays":"Equateur","groupe":"E"},"participant2":{"pays":"France","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4996180836614144,"won":false},"match":{"id":4996180836614144,"date":"2014-06-21T21:00:00.000+02:00","participant1":{"pays":"Allemagne","groupe":"G"},"participant2":{"pays":"Ghana","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5066549580791808,"won":false},"match":{"id":5066549580791808,"date":"2014-05-27T11:16:29.239+02:00","participant1":{"pays":"Cameroun","groupe":"A"},"participant2":{"pays":"Mexique","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5101733952880640,"won":false},"match":{"id":5101733952880640,"date":"2014-06-23T18:00:00.000+02:00","participant1":{"pays":"Australie","groupe":"B"},"participant2":{"pays":"Espagne","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5136918324969472,"won":false},"match":{"id":5136918324969472,"date":"2014-06-18T18:00:00.000+02:00","participant1":{"pays":"Australie","groupe":"B"},"participant2":{"pays":"Pays-Bas","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5207287069147136,"won":false},"match":{"id":5207287069147136,"date":"2014-06-15T21:00:00.000+02:00","participant1":{"pays":"France","groupe":"E"},"participant2":{"pays":"Honduras","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5242471441235968,"won":false},"match":{"id":5242471441235968,"date":"2014-06-25T18:00:00.000+02:00","participant1":{"pays":"Bosnie-et-Herzégovine","groupe":"F"},"participant2":{"pays":"Iran","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5277655813324800,"won":false},"match":{"id":5277655813324800,"date":"2014-06-20T00:00:00.000+02:00","participant1":{"pays":"Honduras","groupe":"E"},"participant2":{"pays":"Equateur","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5348024557502464,"won":false},"match":{"id":5348024557502464,"date":"2014-06-14T21:00:00.000+02:00","participant1":{"pays":"Uruguay","groupe":"D"},"participant2":{"pays":"Costa Rica","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5383208929591296,"won":false},"match":{"id":5383208929591296,"date":"2014-06-24T18:00:00.000+02:00","participant1":{"pays":"Costa Rica","groupe":"D"},"participant2":{"pays":"Angleterre","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5418393301680128,"won":false},"match":{"id":5418393301680128,"date":"2014-06-19T21:00:00.000+02:00","participant1":{"pays":"Uruguay","groupe":"D"},"participant2":{"pays":"Angleterre","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5488762045857792,"won":false},"match":{"id":5488762045857792,"date":"2014-06-16T00:00:00.000+02:00","participant1":{"pays":"Ghana","groupe":"G"},"participant2":{"pays":"USA","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5523946417946624,"won":false},"match":{"id":5523946417946624,"date":"2014-06-26T18:00:00.000+02:00","participant1":{"pays":"USA","groupe":"G"},"participant2":{"pays":"Allemagne","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5559130790035456,"won":false},"match":{"id":5559130790035456,"date":"2014-06-22T18:00:00.000+02:00","participant1":{"pays":"Belgique","groupe":"H"},"participant2":{"pays":"Russie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5629499534213120,"won":false},"match":{"id":5629499534213120,"date":"2014-05-27T11:16:44.239+02:00","participant1":{"pays":"Croatie","groupe":"A"},"participant2":{"pays":"Bresil","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5664683906301952,"won":false},"match":{"id":5664683906301952,"date":"2014-06-23T18:00:00.000+02:00","participant1":{"pays":"Pays-Bas","groupe":"B"},"participant2":{"pays":"Chili","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5699868278390784,"won":false},"match":{"id":5699868278390784,"date":"2014-06-17T00:00:00.000+02:00","participant1":{"pays":"Russie","groupe":"H"},"participant2":{"pays":"République de Corée","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5735052650479616,"won":false},"match":{"id":5735052650479616,"date":"2014-05-27T11:16:29.542+02:00","participant1":{"pays":"Corée du Nord","groupe":"G"},"participant2":{"pays":"Thaïlande","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5770237022568448,"won":false},"match":{"id":5770237022568448,"date":"2014-06-15T18:00:00.000+02:00","participant1":{"pays":"Suisse","groupe":"E"},"participant2":{"pays":"Equateur","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5805421394657280,"won":false},"match":{"id":5805421394657280,"date":"2014-06-25T18:00:00.000+02:00","participant1":{"pays":"Nigéria","groupe":"F"},"participant2":{"pays":"Argentine","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5840605766746112,"won":false},"match":{"id":5840605766746112,"date":"2014-06-20T21:00:00.000+02:00","participant1":{"pays":"Suisse","groupe":"E"},"participant2":{"pays":"France","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5910974510923776,"won":false},"match":{"id":5910974510923776,"date":"2014-06-14T18:00:00.000+02:00","participant1":{"pays":"Colombie","groupe":"C"},"participant2":{"pays":"Grece","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5946158883012608,"won":false},"match":{"id":5946158883012608,"date":"2014-06-24T18:00:00.000+02:00","participant1":{"pays":"Uruguay","groupe":"D"},"participant2":{"pays":"Italie","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5981343255101440,"won":false},"match":{"id":5981343255101440,"date":"2014-06-19T18:00:00.000+02:00","participant1":{"pays":"Colombie","groupe":"C"},"participant2":{"pays":"Côte d'Ivoire","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6051711999279104,"won":false},"match":{"id":6051711999279104,"date":"2014-06-16T21:00:00.000+02:00","participant1":{"pays":"Iran","groupe":"F"},"participant2":{"pays":"Nigéria","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6086896371367936,"won":false},"match":{"id":6086896371367936,"date":"2014-06-26T18:00:00.000+02:00","participant1":{"pays":"Portugal","groupe":"G"},"participant2":{"pays":"Ghana","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6122080743456768,"won":false},"match":{"id":6122080743456768,"date":"2014-06-21T00:00:00.000+02:00","participant1":{"pays":"Nigéria","groupe":"F"},"participant2":{"pays":"Bosnie-et-Herzégovine","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6192449487634432,"won":false},"match":{"id":6192449487634432,"date":"2014-06-13T21:00:00.000+02:00","participant1":{"pays":"Espagne","groupe":"B"},"participant2":{"pays":"Pays-Bas","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6227633859723264,"won":false},"match":{"id":6227633859723264,"date":"2014-06-23T22:00:00.000+02:00","participant1":{"pays":"Cameroun","groupe":"A"},"participant2":{"pays":"Bresil","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6262818231812096,"won":false},"match":{"id":6262818231812096,"date":"2014-06-18T21:00:00.000+02:00","participant1":{"pays":"Espagne","groupe":"B"},"participant2":{"pays":"Chili","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6333186975989760,"won":false},"match":{"id":6333186975989760,"date":"2014-06-15T00:00:00.000+02:00","participant1":{"pays":"Argentine","groupe":"F"},"participant2":{"pays":"Bosnie-et-Herzégovine","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6368371348078592,"won":false},"match":{"id":6368371348078592,"date":"2014-06-25T22:00:00.000+02:00","participant1":{"pays":"Honduras","groupe":"E"},"participant2":{"pays":"Suisse","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6403555720167424,"won":false},"match":{"id":6403555720167424,"date":"2014-06-21T18:00:00.000+02:00","participant1":{"pays":"Argentine","groupe":"F"},"participant2":{"pays":"Iran","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6473924464345088,"won":false},"match":{"id":6473924464345088,"date":"2014-06-14T00:00:00.000+02:00","participant1":{"pays":"Angleterre","groupe":"D"},"participant2":{"pays":"Italie","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6509108836433920,"won":false},"match":{"id":6509108836433920,"date":"2014-06-24T22:00:00.000+02:00","participant1":{"pays":"Japon","groupe":"C"},"participant2":{"pays":"Colombie","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6544293208522752,"won":false},"match":{"id":6544293208522752,"date":"2014-06-19T00:00:00.000+02:00","participant1":{"pays":"Japon","groupe":"C"},"participant2":{"pays":"Grece","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6614661952700416,"won":false},"match":{"id":6614661952700416,"date":"2014-06-17T18:00:00.000+02:00","participant1":{"pays":"Belgique","groupe":"H"},"participant2":{"pays":"Algérie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6649846324789248,"won":false},"match":{"id":6649846324789248,"date":"2014-06-26T22:00:00.000+02:00","participant1":{"pays":"République de Corée","groupe":"H"},"participant2":{"pays":"Belgique","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6685030696878080,"won":false},"match":{"id":6685030696878080,"date":"2014-06-22T21:00:00.000+02:00","participant1":{"pays":"République de Corée","groupe":"H"},"participant2":{"pays":"Algérie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}}];



    var groupeFilter=function(){
        var matchs=[{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4538783999459328,"won":false},"match":{"id":4538783999459328,"date":"2014-06-22T00:00:00.000+02:00","participant1":{"pays":"USA","groupe":"G"},"participant2":{"pays":"Portugal","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4573968371548160,"won":false},"match":{"id":4573968371548160,"date":"2014-06-17T21:00:00.000+02:00","participant1":{"pays":"Bresil","groupe":"A"},"participant2":{"pays":"Mexique","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4609152743636992,"won":false},"match":{"id":4609152743636992,"date":"2014-06-26T22:00:00.000+02:00","participant1":{"pays":"Algérie","groupe":"H"},"participant2":{"pays":"Russie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4644337115725824,"won":false},"match":{"id":4644337115725824,"date":"2014-06-14T03:00:00.000+02:00","participant1":{"pays":"Côte d'Ivoire","groupe":"C"},"participant2":{"pays":"Japon","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4679521487814656,"won":false},"match":{"id":4679521487814656,"date":"2014-06-24T22:00:00.000+02:00","participant1":{"pays":"Grece","groupe":"C"},"participant2":{"pays":"Côte d'Ivoire","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4714705859903488,"won":false},"match":{"id":4714705859903488,"date":"2014-06-20T18:00:00.000+02:00","participant1":{"pays":"Italie","groupe":"D"},"participant2":{"pays":"Costa Rica","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4785074604081152,"won":false},"match":{"id":4785074604081152,"date":"2014-06-13T00:00:00.000+02:00","participant1":{"pays":"Chili","groupe":"B"},"participant2":{"pays":"Australie","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4820258976169984,"won":false},"match":{"id":4820258976169984,"date":"2014-06-23T22:00:00.000+02:00","participant1":{"pays":"Croatie","groupe":"A"},"participant2":{"pays":"Mexique","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4855443348258816,"won":false},"match":{"id":4855443348258816,"date":"2014-06-18T00:00:00.000+02:00","participant1":{"pays":"Cameroun","groupe":"A"},"participant2":{"pays":"Croatie","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4925812092436480,"won":false},"match":{"id":4925812092436480,"date":"2014-06-16T18:00:00.000+02:00","participant1":{"pays":"Allemagne","groupe":"G"},"participant2":{"pays":"Portugal","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4960996464525312,"won":false},"match":{"id":4960996464525312,"date":"2014-06-25T22:00:00.000+02:00","participant1":{"pays":"Equateur","groupe":"E"},"participant2":{"pays":"France","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":4996180836614144,"won":false},"match":{"id":4996180836614144,"date":"2014-06-21T21:00:00.000+02:00","participant1":{"pays":"Allemagne","groupe":"G"},"participant2":{"pays":"Ghana","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5066549580791808,"won":false},"match":{"id":5066549580791808,"date":"2014-05-27T11:16:29.239+02:00","participant1":{"pays":"Cameroun","groupe":"A"},"participant2":{"pays":"Mexique","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5101733952880640,"won":false},"match":{"id":5101733952880640,"date":"2014-06-23T18:00:00.000+02:00","participant1":{"pays":"Australie","groupe":"B"},"participant2":{"pays":"Espagne","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5136918324969472,"won":false},"match":{"id":5136918324969472,"date":"2014-06-18T18:00:00.000+02:00","participant1":{"pays":"Australie","groupe":"B"},"participant2":{"pays":"Pays-Bas","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5207287069147136,"won":false},"match":{"id":5207287069147136,"date":"2014-06-15T21:00:00.000+02:00","participant1":{"pays":"France","groupe":"E"},"participant2":{"pays":"Honduras","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5242471441235968,"won":false},"match":{"id":5242471441235968,"date":"2014-06-25T18:00:00.000+02:00","participant1":{"pays":"Bosnie-et-Herzégovine","groupe":"F"},"participant2":{"pays":"Iran","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5277655813324800,"won":false},"match":{"id":5277655813324800,"date":"2014-06-20T00:00:00.000+02:00","participant1":{"pays":"Honduras","groupe":"E"},"participant2":{"pays":"Equateur","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5348024557502464,"won":false},"match":{"id":5348024557502464,"date":"2014-06-14T21:00:00.000+02:00","participant1":{"pays":"Uruguay","groupe":"D"},"participant2":{"pays":"Costa Rica","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5383208929591296,"won":false},"match":{"id":5383208929591296,"date":"2014-06-24T18:00:00.000+02:00","participant1":{"pays":"Costa Rica","groupe":"D"},"participant2":{"pays":"Angleterre","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5418393301680128,"won":false},"match":{"id":5418393301680128,"date":"2014-06-19T21:00:00.000+02:00","participant1":{"pays":"Uruguay","groupe":"D"},"participant2":{"pays":"Angleterre","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5488762045857792,"won":false},"match":{"id":5488762045857792,"date":"2014-06-16T00:00:00.000+02:00","participant1":{"pays":"Ghana","groupe":"G"},"participant2":{"pays":"USA","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5523946417946624,"won":false},"match":{"id":5523946417946624,"date":"2014-06-26T18:00:00.000+02:00","participant1":{"pays":"USA","groupe":"G"},"participant2":{"pays":"Allemagne","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5559130790035456,"won":false},"match":{"id":5559130790035456,"date":"2014-06-22T18:00:00.000+02:00","participant1":{"pays":"Belgique","groupe":"H"},"participant2":{"pays":"Russie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5629499534213120,"won":false},"match":{"id":5629499534213120,"date":"2014-05-27T11:16:44.239+02:00","participant1":{"pays":"Croatie","groupe":"A"},"participant2":{"pays":"Bresil","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5664683906301952,"won":false},"match":{"id":5664683906301952,"date":"2014-06-23T18:00:00.000+02:00","participant1":{"pays":"Pays-Bas","groupe":"B"},"participant2":{"pays":"Chili","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5699868278390784,"won":false},"match":{"id":5699868278390784,"date":"2014-06-17T00:00:00.000+02:00","participant1":{"pays":"Russie","groupe":"H"},"participant2":{"pays":"République de Corée","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5735052650479616,"won":false},"match":{"id":5735052650479616,"date":"2014-05-27T11:16:29.542+02:00","participant1":{"pays":"Corée du Nord","groupe":"G"},"participant2":{"pays":"Thaïlande","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5770237022568448,"won":false},"match":{"id":5770237022568448,"date":"2014-06-15T18:00:00.000+02:00","participant1":{"pays":"Suisse","groupe":"E"},"participant2":{"pays":"Equateur","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5805421394657280,"won":false},"match":{"id":5805421394657280,"date":"2014-06-25T18:00:00.000+02:00","participant1":{"pays":"Nigéria","groupe":"F"},"participant2":{"pays":"Argentine","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5840605766746112,"won":false},"match":{"id":5840605766746112,"date":"2014-06-20T21:00:00.000+02:00","participant1":{"pays":"Suisse","groupe":"E"},"participant2":{"pays":"France","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5910974510923776,"won":false},"match":{"id":5910974510923776,"date":"2014-06-14T18:00:00.000+02:00","participant1":{"pays":"Colombie","groupe":"C"},"participant2":{"pays":"Grece","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5946158883012608,"won":false},"match":{"id":5946158883012608,"date":"2014-06-24T18:00:00.000+02:00","participant1":{"pays":"Uruguay","groupe":"D"},"participant2":{"pays":"Italie","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":5981343255101440,"won":false},"match":{"id":5981343255101440,"date":"2014-06-19T18:00:00.000+02:00","participant1":{"pays":"Colombie","groupe":"C"},"participant2":{"pays":"Côte d'Ivoire","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6051711999279104,"won":false},"match":{"id":6051711999279104,"date":"2014-06-16T21:00:00.000+02:00","participant1":{"pays":"Iran","groupe":"F"},"participant2":{"pays":"Nigéria","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6086896371367936,"won":false},"match":{"id":6086896371367936,"date":"2014-06-26T18:00:00.000+02:00","participant1":{"pays":"Portugal","groupe":"G"},"participant2":{"pays":"Ghana","groupe":"G"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6122080743456768,"won":false},"match":{"id":6122080743456768,"date":"2014-06-21T00:00:00.000+02:00","participant1":{"pays":"Nigéria","groupe":"F"},"participant2":{"pays":"Bosnie-et-Herzégovine","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6192449487634432,"won":false},"match":{"id":6192449487634432,"date":"2014-06-13T21:00:00.000+02:00","participant1":{"pays":"Espagne","groupe":"B"},"participant2":{"pays":"Pays-Bas","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6227633859723264,"won":false},"match":{"id":6227633859723264,"date":"2014-06-23T22:00:00.000+02:00","participant1":{"pays":"Cameroun","groupe":"A"},"participant2":{"pays":"Bresil","groupe":"A"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6262818231812096,"won":false},"match":{"id":6262818231812096,"date":"2014-06-18T21:00:00.000+02:00","participant1":{"pays":"Espagne","groupe":"B"},"participant2":{"pays":"Chili","groupe":"B"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6333186975989760,"won":false},"match":{"id":6333186975989760,"date":"2014-06-15T00:00:00.000+02:00","participant1":{"pays":"Argentine","groupe":"F"},"participant2":{"pays":"Bosnie-et-Herzégovine","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6368371348078592,"won":false},"match":{"id":6368371348078592,"date":"2014-06-25T22:00:00.000+02:00","participant1":{"pays":"Honduras","groupe":"E"},"participant2":{"pays":"Suisse","groupe":"E"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6403555720167424,"won":false},"match":{"id":6403555720167424,"date":"2014-06-21T18:00:00.000+02:00","participant1":{"pays":"Argentine","groupe":"F"},"participant2":{"pays":"Iran","groupe":"F"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6473924464345088,"won":false},"match":{"id":6473924464345088,"date":"2014-06-14T00:00:00.000+02:00","participant1":{"pays":"Angleterre","groupe":"D"},"participant2":{"pays":"Italie","groupe":"D"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6509108836433920,"won":false},"match":{"id":6509108836433920,"date":"2014-06-24T22:00:00.000+02:00","participant1":{"pays":"Japon","groupe":"C"},"participant2":{"pays":"Colombie","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6544293208522752,"won":false},"match":{"id":6544293208522752,"date":"2014-06-19T00:00:00.000+02:00","participant1":{"pays":"Japon","groupe":"C"},"participant2":{"pays":"Grece","groupe":"C"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6614661952700416,"won":false},"match":{"id":6614661952700416,"date":"2014-06-17T18:00:00.000+02:00","participant1":{"pays":"Belgique","groupe":"H"},"participant2":{"pays":"Algérie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6649846324789248,"won":false},"match":{"id":6649846324789248,"date":"2014-06-26T22:00:00.000+02:00","participant1":{"pays":"République de Corée","groupe":"H"},"participant2":{"pays":"Belgique","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}},{"bet":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"matchId":6685030696878080,"won":false},"match":{"id":6685030696878080,"date":"2014-06-22T21:00:00.000+02:00","participant1":{"pays":"République de Corée","groupe":"H"},"participant2":{"pays":"Algérie","groupe":"H"},"outcome":{"score1":{"participant":null,"unknown":true,"score":""},"score2":{"participant":null,"unknown":true,"score":""},"updated":false}}}];

       /* $scope.matchsBets = _.groupBy(matchs, function (matchBet) {
            return matchBet.match.participant1.groupe;
        })*/
        $scope.matchsBets={all:matchs};
    }

    groupeFilter();


    $scope.$watch('showAll()',function(newValue,oldValue){
        console.log('showAll change detected '+newValue)
        if(newValue!=oldValue) {
            if (newValue == true) {
                $scope.matchsBets = {all:_.flatten(_.values($scope.matchsBets))}

            }
            else {
                if (newValue == false) {
                    var matchsBets = _.flatten(_.values($scope.matchsBets));
                    $scope.matchsBets = _.groupBy(matchsBets, function (matchBet) {
                        return matchBet.match.participant1.groupe;
                    })
                }
            }
        }
    },true)


    Gambler.get().$promise
        .then(function (response) {
            return response;
        })
        .then(onGamblerRetrieved)
        .catch(function () {
        })

    function onGamblerRetrieved(gambler) {
        $rootScope.user.points = gambler.points;

    }

    var fetchMatchs = function () {

        var defer = $q.defer();
        defer.promise
            .then(function () {
                var clMatch = angular.copy($scope.matchsBets);
                return clMatch;

            })
            .then(function (clMatch) {
                var srvMatchs = betMatchService.getAll();
                return {srvMatchs: srvMatchs, clMatch: clMatch};

            })
            .then(function (matchLists) {
                $scope.matchsBets = matchLists.srvMatchs;
                return matchLists;
            })
            .then(function (matchLists) {
                betMatchService.signalUnreg(matchLists.srvMatchs, matchLists.clMatch)
            })


            .catch(function () {
                console.log('error retrieving bets from server');
            })
        defer.resolve();
    }

   /* $scope.groupes = [{lettre:"A",checked:false}, {lettre:"B",checked:false}, {lettre:"C",checked:false}, {lettre:"D",checked:false}, {lettre:"E",checked:false}, {lettre:"F",checked:false},
        {lettre:"G",checked:false},{lettre:"H",checked:false}];*/

    $scope.groupes = {
        A: {checked:false,letter:'A'},
        B: {checked:false,letter:'B'},
        C:{checked:false,letter:'C'},
        D:{checked:false,letter:'D'},
        E:{checked:false,letter:'E'},
        F:{checked:false,letter:'F'},
        G:{checked:false,letter:'G'},
        H:{checked:false,letter:'H'}
    }





    var showAll=function(){
        var toRet=true;
        for(var index in $scope.groupes){
            toRet = toRet&&!$scope.groupes[index].checked
        }
        return toRet;
    }

    $scope.showAll=showAll;

    $scope.scoreRegexp = /^[0-9]{1,2}$|^$/;


    /**
     * This function get bets from the server and compare them to the bets client side,
     * in order to identify bets which were not registered (usefull to identify bets which were
     * voted but were posted after the beginning of the match
     */
    var updateBets = function () {
        var matchAndBets = betMatchService.getAll().$promise;
        matchAndBets.then(function (result) {
            var matchBetsCl = angular.copy($scope.matchsBets);
            return {result: result, matchBetsCl: matchBetsCl};

        })
            .then(function (couple) {
                $scope.matchsBets = couple.result;
                return couple;
            })
            .then(function (couple) {
                betMatchService.markUnreg(couple.matchBetsCl, $scope.matchsBets);
            })

    }


    /**
     * Function called when "postez" is clicked
     */
    $scope.postez = function () {
        $scope.modified = false;
        submit();
    }


    /**
     * Function called via $scope.postez() when "postez" is clicked
     */
    var submit = function () {
        checkScores(postBets);

    }
    /**
     * This variable says whether or not a 0 value has been assigned automatically to a score which was not assigned
     * It is put to false again every time the "postez" button is clicked.
     * @type {boolean}
     */
    $scope.modified = false;


    /**
     * Posts the result to the restx backend. Called within checkscores, only if no score was assigned 0 value automatically
     */
    var postBets = function () {

        postBetService.save($scope.matchsBets, function () {
            console.log('bets were sucessfully sent');
            // $scope.matchsBets = betMatchService.getAll();
            updateBets();
        }, function () {
            console.log('sending bets failed');
        })
    }

    var checkScores = function (callBack) {


        angular.forEach($scope.matchsBets, checkScore);
        if (!$scope.modified) {
            callBack();
        }
    }


    var checkScore = function (matchAndBet) {

        var unsetScore = scoreUnset(matchAndBet);
        if (unsetScore) {
            unsetScore.score = 0;
            unsetScore.assignedZero = true;
            return true;
        }
        else {
            return false;
        }
    }

    var scoreUnset = function (matchAndBet) {
        var bool1;
        bool1 = matchAndBet.bet.score1.score === '' && matchAndBet.bet.score2.score !== '';

        if (bool1) {
            $scope.modified = true;
            return matchAndBet.bet.score1;
        }

        var bool2;
        bool2 = matchAndBet.bet.score2.score === '' && matchAndBet.bet.score1.score !== '';

        if (bool2) {
            $scope.modified = true;
            return matchAndBet.bet.score2;
        }

        return undefined;
    }

    /**
     * Returns true if one of the scores was assigned a zero value automatically
     * @param bet
     * @returns {boolean|*}
     */
    $scope.showWarning = function (bet) {

        return bet.score1.assignedZero || bet.score2.assignedZero;
        //return true;
    }

    $scope.isFormer = function (date) {
        var now = new Date();
        var matchDate = new Date(date);

        var toRet = matchDate <= now;

        return toRet;
    }

    /**
     * Says whether the formular can be validated (necessary condition). If one score is set, the other one has to
     * be as well.
     * @param bet
     * @returns {boolean}
     */
    $scope.hasTwoScores = function (bet) {
        var sc1Empty = !bet.score1.score || bet.score1.score.trim() == ""
        var sc2empty = !bet.score2.score || bet.score2.score.trim() == "";
        var notOk = (sc1Empty && !sc2empty) || (!sc1Empty && sc2empty)

        return notOk;
    }


    $scope.knownResult = function (match) {
        return $scope.isFormer(match.date) && match.outcome;
    }

    $scope.isWinner = displayService.isWinner;

    $scope.calculatePoints = betMatchService.calculatePoints;

    $scope.dispPoints=displayService.dispPoints;



}]);


controllers.controller('navBarController', function ($scope, $location, isActiveService) {
    $scope.isActive = function (path) {
        isActiveService.isActive(path);
    }

});

