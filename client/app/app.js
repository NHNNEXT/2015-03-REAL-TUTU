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
    'ui.bootstrap',
    'md.data.table',
    'datePicker',
    'ngFileUpload',
    'restangular',
    'toastr',
    'scDateTime',
    'anim-in-out'
  ]).run(function ($rootScope, $state) {
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
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

  }).value('froalaConfig', {
    placeholder: '글을 작성해 주세요.',
    language: 'ko',
    imageUploadURL: '/api/v1/upload',
    fontList: {
      "'Nanum Gothic', sans-serif": '본고딕',
      "'Nanum Myeongjo', serif": '나눔명조'
    },
    buttons: ['fontFamily', 'fontSize', 'bold', 'italic', 'underline', 'strikeThrough', "sep", '|', 'color', '|', 'paragraphFormat', 'align', 'outdent', 'indent', 'insertImage', 'html']
  });
}());

