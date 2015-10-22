angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('notfound', {
      url: "/notfound",
      templateUrl: "/pages/notfound/notfound.html",
      controller: function () {

      }
    });
});
