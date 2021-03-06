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
    'ngFileUpload',
    'toastr',
    'anim-in-out'
    /* @ngInject */
  ]).run(function ($rootScope, $state, confirm, $window, pageMove, Loading, rootUser, $location) {
    $($window).on("beforeunload", function () {
      if ($state.current.exitConfirm)
        return "이 페이지에서 벗어나면 작성하신 내용은 저장되지 않습니다.";
    });
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
        $state.current = toState;
        pageMove.ok = false;
        Loading.end();
        $window.ga('send', 'pageview', {page: $location.url()});
      }
    );
    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState) {
      if (!rootUser.isLogged() && toState.loginneed) {
        event.preventDefault();
        $state.go("loginneed");
        return;
      }
      if (!pageMove.ok && fromState.exitConfirm) {
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

