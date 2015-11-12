angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profile', {
      url: "/profile/:id",
      controller:'profileController',
      templateUrl: "/user.page.profile/profile.html"
    });
});
