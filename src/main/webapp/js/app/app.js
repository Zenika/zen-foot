(function () { 'use strict';

angular.module('zenFoot.app', [
    'zenFoot.controllers', 'zenFoot.services', 'zenContact.filters', 'zenFoot.directives',
    'ngCookies', 'ngResource', 'ngRoute',
    'ui.router', 'ui.bootstrap', 'ui.unique',
    'ngGrid', 'angular-md5',
])

.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $stateProvider
        .state('loginState', {
            url: '/login',
            templateUrl: 'view/login.html'
        })
        .state('indexState', {
            url: '/index',
            templateUrl: 'view/index-content.html'
        })
        .state('adminState', {
            url: '/admin',
            templateUrl: 'view/admin-tmp.html'
        })
        .state('classState',{
            url: '/classement',
            templateUrl: 'view/classement.html'
        })
    $urlRouterProvider.otherwise('/index')

    $httpProvider.interceptors.push(function ($q, authService, $rootScope, $location) {
        return {
            responseError: function (rejection) {
                if (rejection.status === 403 && $rootScope.isAdmin()) {
                    $location.path('/admin')
                } else if (rejection.status === 401 || rejection.status === 403) {
                    authService.redirectToLogin()
                }
                return $q.reject(rejection)
            },
        }
    })
})

.run(function ($rootScope, $state) {
    var adminRoute = 'adminState'
    var loginRoute = 'loginState'
    $rootScope.$on('$stateChangeStart', function (evt, toState, toParams, fromState, fromParams) {
        if ($rootScope.isConnected() && toState.name === loginRoute) {
            evt.preventDefault()
            $state.go(fromState.name)
        } else if (!$rootScope.isConnected() && toState.name !== loginRoute) {
            evt.preventDefault()
            $state.go(loginRoute)
        } else if ($rootScope.isConnected() && $rootScope.isAdmin() && toState.name !== adminRoute) {
            evt.preventDefault()
            $state.go(adminRoute)
        }
    })

    // Makes the navbar collapse when a link is clicked
    // See https://github.com/twbs/bootstrap/issues/9013
    // Comment from danielward on Aug 8, 2013
    $('.navbar a').click(function () {
        var navbar_toggle = $('.navbar-toggle')
        if (navbar_toggle.is(':visible')) {
            navbar_toggle.trigger('click')
        }
    })
})

}())
