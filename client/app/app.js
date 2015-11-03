/**
 * @ngdoc overview
 * @name clientApp
 * @description
 * # clientApp
 *
 * Main module of the application.
 */
 (function() {
   'use strict';
   angular.module('clientApp', [
     'ngAnimate',
     'ngCookies',
     'ngResource',
     'ngSanitize',
     'ngTouch',
     'ui.router',
     'ui.bootstrap',
     'wysiwyg.module',
     'datePicker',
     'ngFileUpload',
     'restangular',
     'toastr'
   ]).run(function ($rootScope, $state) {
     $rootScope.$on('$stateChangeSuccess',
       function (event, toState) {
         //function (event, toState, toParams, fromState, fromParams) {
         $state.current = toState;
       }
     );
     $rootScope.$on('$stateChangeStart', function (event, toState) {
       var redirect = toState.redirectTo;
       if (redirect) {
         event.preventDefault();
         if (angular.isFunction(redirect))
           redirect.call($state);
         else
           $state.go(redirect);
       }
     });
   }).config(function ($locationProvider, $urlRouterProvider, RestangularProvider,toastrConfig) {
     $locationProvider.html5Mode({
       enabled: true,
       requireBase: false
     });
     RestangularProvider.setBaseUrl('/api/v1/');
     $urlRouterProvider.otherwise("/notfound");
     angular.extend(toastrConfig, {
       timeOut: 3000,
       closeButton: true,
     });
   });
 }());
