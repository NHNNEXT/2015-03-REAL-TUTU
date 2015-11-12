angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('contentEdit', {
      url: "/content/:id/edit",
      templateUrl: "/content.page.edit/edit.html",
      controller: 'contentEditController'
    })
    .state('contentNew', {
      url: "/content/write",
      templateUrl: "/content.page.edit/edit.html",
      controller: 'contentEditController'
    });
});
