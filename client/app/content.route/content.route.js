/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('contentNew', {
      url: "/content/:lectureId/write",
      templateUrl: "/content.page.edit/write.html",
      controller: 'contentWriteController'
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
