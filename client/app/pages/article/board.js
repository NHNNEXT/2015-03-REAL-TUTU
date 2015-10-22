angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('articleBoard', {
      url: "/article/board/:id",
      templateUrl: "/pages/article/board.html",
      controller: function () {

      }
    });
});
