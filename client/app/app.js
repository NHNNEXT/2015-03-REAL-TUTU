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
    'anim-in-out',
    'ui.bootstrap.accordion',
    'ui.bootstrap.collapse'
    /* @ngInject */
  ]).run(function ($rootScope, $state, confirm, $window, pageMove, Loading, rootUser) {
    $($window).on("beforeunload", function () {
      if ($state.current.exitConfirm)
        return "이 페이지에서 벗어나면 작성하신 내용은 저장되지 않습니다.";
    });
    $rootScope.dynamicTheme = "default";
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
        $state.current = toState;
        pageMove.ok = false;
        Loading.end();
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
    $mdThemingProvider.theme('editor')
      .primaryPalette('brown', {
        'hue-2': '200'
      })
      .accentPalette('pink')
      .warnPalette('red')
      .backgroundPalette('grey');

    $mdThemingProvider.alwaysWatchTheme(true);

  });
}());

