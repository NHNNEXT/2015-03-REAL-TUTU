(function () {
  'use strict';
  angular.module('clientApp', [
    'ngAnimate',
    'ngCookies',
    'ngMaterial',
    'ngMessages',
    'ngResource',
    'ngSanitize',
    'ngTouch',
    'ui.router',
    'md.data.table',
    'datePicker',
    'ngFileUpload',
    'restangular',
    'toastr',
    'scDateTime',
    'anim-in-out'
    /* @ngInject */
  ]).run(function ($rootScope, $state, history, $location) {
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
        $state.current = toState;
        history.pushIfNotExist($location.path());
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
    /* @ngInject */
  }).config(function ($locationProvider, $urlRouterProvider, RestangularProvider, toastrConfig, $httpProvider, $mdThemingProvider) {
    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });
    RestangularProvider.setBaseUrl('/api/v1/');
    $urlRouterProvider.otherwise("/notfound");
    angular.extend(toastrConfig, {
      timeOut: 3000,
      closeButton: true
    });
    $httpProvider.interceptors.push('httpInterceptor');
    $mdThemingProvider.theme('default');
    //.primaryPalette('pink')
    //.accentPalette('orange');
  });
}());

