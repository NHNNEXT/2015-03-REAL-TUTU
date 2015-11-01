angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lectureEdit', {
      url: "/lecture/:id/edit",
      templateUrl: "/lecture/page/lectureEdit.html",
      controller: function () {
      }
    })
    .state('lectureNew', {
      url: "/lecture/new",
      templateUrl: "/lecture/page/lectureEdit.html",
      controller: function () {
      }
    });
});


