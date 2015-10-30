angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lectureEdit', {
      url: "/lecture/:id/edit",
      templateUrl: "/pages/lecture/lectureEdit.html",
      controller: function () {
      }
    })
    .state('lectureNew', {
      url: "/lecture/new",
      templateUrl: "/pages/lecture/lectureEdit.html",
      controller: function () {
      }
    });
});


