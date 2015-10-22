angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture', {
      url: "/lecture/:id",
      templateUrl: "/pages/lecture/lecture.html",
      controller: function () {
      }
    });
});
