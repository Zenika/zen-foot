'use strict'

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

.run(function ($rootScope, authService,$location,$state) {

    var adminRoute = 'adminState'
    var loginRoute = 'loginState'

    $rootScope.loggedIn = authService.loggedIn
    $rootScope.logOut = authService.logOut

    $rootScope.$on('$stateChangeSuccess', function (evt, toState, toParams, fromState, fromParams) {

        console.log('ici')
        console.log($rootScope.user)
        console.log($rootScope.isAdmin())

        if ($rootScope.isConnected() && toState.name === loginRoute) {
            console.log('login page is not auth. if connected')
            evt.preventDefault()
            $state.transitionTo(fromState.name)
        }

        if (!$rootScope.isConnected() && toState.name !== loginRoute) {
            console.log('redirecting to login')
            evt.preventDefault()
            $state.transitionTo(loginRoute)
        } else {
            console.log(toState)
            if ($rootScope.isConnected() && $rootScope.isAdmin() && toState.name !== adminRoute) {
                console.log('redirecting admin')
                evt.preventDefault()
                $state.transitionTo(adminRoute)
            }
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
