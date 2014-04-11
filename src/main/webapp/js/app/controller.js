/**
 * 
 */

'use strict';
var controllers = angular.module('controllers', []);

controllers.controller('SimpleController',[
 '$scope',
 'HelloNHour',
 'updateSentenceService',
 function($scope, HelloNHour){
	$scope.sentence=HelloNHour.update({ who: $scope.nom });
	$scope.$watch('nom', function (newValue) {
		$scope.sentence=HelloNHour.update({who: newValue});
	});
	
}]);


controllers.controller('LogginController',['$scope', 'authService', '$http',function($scope, authService, $http){
	$scope.login=function(user){
		$http.post('/rest/login/'+user).success(function(){authService.redirectToHome()});
	};
}]);

controllers.controller('ContactListController', [
		'$scope',
		'Contact',
		function($scope, Contact) {
			
			$scope.contacts=Contact.query();
			
			$scope.time='16:06';
			
			$scope.nameFilter = function(contact) {
				if (!$scope.search) {
					return true;
				}
				return contact.firstName.match(new RegExp($scope.search, 'i'))
						|| contact.lastName
								.match(new RegExp($scope.search, 'i'));

			};
			
			

		} ]);

	var wrappingFunc=function(Contact, value){
		console.log(value);
		return Contact.get({
			id : value
		});
	};

controllers.controller('ContactEditController', function($scope,
		Contact,$http, authService, contactId, $location) {
	
	$scope.contact =wrappingFunc(Contact, contactId.value);
	
	

	$scope.saveContact = function(contact) {
		contact.$update(function() {
			// $location.path('/list');
		});
	};
	
	$scope.deleteContact=function(contact){
		if(contact){
			$http.defaults.headers.delete={'Auth-Token':authService.loggedIn()};
			contact.$delete(function(){
				$location.path('/list');
			});
		}
	};

	/*
	 * USES contactService
	 * contactService.getContactById($routeParams.id,function(contact){
	 * $scope.contact=contact; });
	 * 
	 * $scope.saveContact=function(contact){
	 * contactService.saveContact(contact); $location.path('/view/list.html'); };
	 */

});

controllers.controller('ContactAddController', function($scope, Contact,
		$location) {

	/*
	 * $scope.contact={};
	 * 
	 * $scope.saveContact=function(contact){
	 * contactService.saveContact(contact); $location.path('/view/list.html'); };
	 */
	$scope.contact = new Contact();
	$scope.saveContact = function(contact) {

		Contact.save(contact, function() {
			$location.path("/list");
		});
	};
});



controllers.controller('navBarController', function($scope, $location, isActiveService) {
	$scope.isActive = function(path){
		isActiveService.isActive(path);
	}

});

controllers.controller('logOutController',function(){
	
});