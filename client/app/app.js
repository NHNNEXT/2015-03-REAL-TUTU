'use strict';

/**
 * @ngdoc overview
 * @name clientApp
 * @description
 * # clientApp
 *
 * Main module of the application.
 */
angular.module('clientApp', [
  'ngAnimate',
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ngTouch',
  'ui.router',
  'wysiwyg.module',
  'datePicker'
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
}).config(function ($locationProvider, $urlRouterProvider) {
  $locationProvider.html5Mode({
    enabled: true,
    requireBase: false
  });

  $urlRouterProvider.otherwise("/notfound");
});
