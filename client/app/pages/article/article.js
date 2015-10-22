angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('article', {
      url: "/article/:id",
      templateUrl: "/pages/article/article.html",
      controller: function () {

      }
    });
});
