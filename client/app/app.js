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
    'ngFileUpload',
    'toastr',
    'anim-in-out'
    /* @ngInject */
  ]).run(function ($rootScope, $state) {
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
        $state.current = toState;
      }
    );
    $rootScope.$on('$stateChangeStart', function(event, toState, toParams) {
      if (toState.redirectTo) {
        event.preventDefault();
        $state.go(toState.redirectTo, toParams);
      }
    });
    /* @ngInject */
  }).config(function ($locationProvider, toastrConfig, $httpProvider, $mdThemingProvider) { //RestangularProvider,
    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });
    //RestangularProvider.setBaseUrl('/api/v1/');
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

