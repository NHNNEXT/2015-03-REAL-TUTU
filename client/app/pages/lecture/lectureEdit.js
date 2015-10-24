angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lectureEdit', {
      url: "/lecture/edit/:id",
      templateUrl: "/pages/lecture/lectureEdit.html",
      controller: function () {
      }
    });
});


