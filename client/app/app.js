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
    'anim-in-out',
    'satellizer'
    /* @ngInject */
  ]).run(function ($rootScope, $state) {
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
        $state.current = toState;
      }
    );
    /* @ngInject */
  }).config(function ($locationProvider, $urlRouterProvider, RestangularProvider, toastrConfig, $httpProvider, $mdThemingProvider) {
    $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    });
    RestangularProvider.setBaseUrl('/api/v1/');
    $urlRouterProvider.otherwise("/페이지를찾으려는노오오력이부족하다");
    angular.extend(toastrConfig, {
      timeOut: 3000,
      closeButton: true
    });
    $httpProvider.interceptors.push('httpInterceptor');
    $mdThemingProvider.theme('default');
    //.primaryPalette('pink')
    //.accentPalette('orange');
  }).config(function($authProvider) {

    $authProvider.google({
      clientId: '255146844316-5kqt3k3sksejf7i1l9alnv1bmv39usom.apps.googleusercontent.com'
    });
  });
    //$authProvider.oauth2({
    //  name: 'youtube',
    //  url: '/auth/foursquare',
    //  clientId: '255146844316-5kqt3k3sksejf7i1l9alnv1bmv39usom.apps.googleusercontent.com',
    //  redirectUri: window.location.origin,
    //  authorizationEndpoint: 'https://foursquare.com/oauth2/authenticate',
    //});
}());

