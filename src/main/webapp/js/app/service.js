'use strict';

// Contact service

var zenContactService = angular.module("zenContact.services", [ 'ngResource' ]);

zenContactService.service("contactService", [ '$http', function($http) {
	this.contacts = [];

	this.getAllContacts = function(fonction) {
		$http({
			method : 'GET',
			url : '/rest/contacts'
		}).success(function(contacts) {
			fonction(contacts);
		});
	};

	this.getContactById = function(id, fonctionCBack) {
		$http({
			method : 'GET',
			url : '/rest/contacts/' + id
		}).success(function(contact) {
			fonctionCBack(contact);
		}).error(function() {
			fonctionCBack({});
		});
	};

	this.indexOf = function(contact) {
		if (contact.id == undefined)
			return -1;
		for (var i = 0; i < this.contacts.length; i++) {
			if (this.contacts[i].id == contact.id) {
				return i;
			}
			return -1;
		}
	};

	this.saveContact = function(contact) {
		if (contact.id) {
			$http({
				method : 'PUT',
				url : '/rest/contacts/' + id
			});
		} else {
			$http({
				method : 'POST',
				url : '/rest/contacts',
				data : contact
			});
		}
	};

} ]);

// Contact resource

zenContactService.factory('Contact', [ '$resource', function($resource) {

	return $resource('/rest/contacts/:id', {
		id : '@id'
	}, {
		update : {
			method : 'PUT',
			id : '@id'
		}
	});
} ]);

zenContactService.factory('authService', function($location, $cookieStore) {
	return {
		redirectToLogin : function() {
			$location.path('/login');
		},

		redirectToHome : function() {
			$location.path('/list');
		},

		storeToken : function(response) {
			var token = response.headers('Auth-Token');
			if (token) {
				$cookieStore.put("Auth-Token", token);
			}
		},

		logOut : function() {
			$cookieStore.remove("Auth-Token");
		},

		loggedIn : function() {
			return $cookieStore.get("Auth-Token");
		}
	};

});

zenContactService.factory('isActiveService', function($location) {
	return {
		isActive : function(path) {
			return $location.path().indexOf(path) != -1;

		},

		time : function() {
			var date = new Date();

			return date.getHours() + ":" + date.getMinutes + ":"
					+ date.getSeconds();
		}
	};
});


