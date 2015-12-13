/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('main', {
      url: "/",
      templateUrl: "/common.page.main/main.html",
      controller: 'mainController',
      controllerAs: 'main',
      header: "내 강의 일정"
    });
});
