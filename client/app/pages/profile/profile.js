angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profile', {
      url: "/profile/:id",
      templateUrl: "/pages/profile/profile.html",
      controller: function () {
      }
    });
});
