/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('contentNew', {
      url: "/content/write",
      templateUrl: "/content.page.edit/edit.html",
      controller: 'contentEditController'
    })
    .state('content', {
      url: "/content/:id",
      templateUrl: "/content.page.detail/detail.html",
      controller: "contentDetailController"
    })
    .state('contentEdit', {
      url: "/content/:id/edit",
      templateUrl: "/content.page.edit/edit.html",
      controller: 'contentEditController'
    });

});
