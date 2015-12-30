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
  ]).run(function ($rootScope, $state, confirm, $window, pageMove, Loading) {
    $($window).on("beforeunload", function () {
      if ($state.current.confirm)
        return "이 페이지에서 벗어나면 작성하신 내용은 저장되지 않습니다.";
    });
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
        $state.current = toState;
        pageMove.ok = false;
        Loading.end();
      }
    );
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState) {
      if (!pageMove.ok && fromState.confirm) {
        event.preventDefault();
        confirm("이 페이지에서 벗어나면 작성하신 내용은 저장되지 않습니다.", undefined, function () {
          pageMove.ok = true;
          $state.go(toState.name, toParams);
        });
        return;
      }
      if (toState.redirectTo) {
        event.preventDefault();
        $state.go(toState.redirectTo, toParams);
      }
      Loading.start();

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

