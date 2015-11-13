/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lectureEdit', {
      url: "/lecture/:id/edit",
      templateUrl: "/lecture.page.edit/edit.html",
      controller: 'editLectureController'
    })
    .state('lectureNew', {
      url: "/newLecture",
      templateUrl: "/lecture.page.edit/edit.html",
      controller: 'editLectureController'
    });
});
