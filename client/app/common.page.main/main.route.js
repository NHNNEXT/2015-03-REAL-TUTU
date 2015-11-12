/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('main', {
      url: "/",
      templateUrl: "/common.page.main/main.html",
      controller: 'mainController'
    });
});
