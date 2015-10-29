angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('register', {
      url: "/user/register",
      templateUrl: "/pages/register/register.html",
      controller: function ($scope, regex, user, alert, http) {
      }
    });
});
