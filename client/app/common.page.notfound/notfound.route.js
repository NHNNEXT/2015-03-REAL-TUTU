/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('notfound', {
      url: "/notfound",
      templateUrl: "/common.page.notfound/notfound.html"
    });
});
