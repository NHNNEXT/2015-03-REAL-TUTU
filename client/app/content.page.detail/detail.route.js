/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('content', {
      url: "/content/:id",
      templateUrl: "/content.page.detail/detail.html",
      controller: "contentDetailController"
    });
});
