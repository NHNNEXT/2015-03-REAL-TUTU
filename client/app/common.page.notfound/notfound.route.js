/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('notfound', {
      url: "/페이지를찾으려는노오오력이부족하다",
      templateUrl: "/common.page.notfound/notfound.html",
      header:"404 NOT FOUND"
    });
});
