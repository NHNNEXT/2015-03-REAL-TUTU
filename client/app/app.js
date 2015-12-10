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
    /* @ngInject */
  }).config(function ($locationProvider, $urlRouterProvider,  toastrConfig, $httpProvider, $mdThemingProvider) { //RestangularProvider,
    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });
    //RestangularProvider.setBaseUrl('/api/v1/');
    $urlRouterProvider.otherwise("/페이지를찾으려는노오오력이부족하다");
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

