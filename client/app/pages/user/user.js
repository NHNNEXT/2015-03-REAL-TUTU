angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('user', {
      url: "/user/:id",
      templateUrl: "/pages/user/user.html",
      controller: function () {
      }
    });
});
