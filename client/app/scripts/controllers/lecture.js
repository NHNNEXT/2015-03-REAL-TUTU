
/**
* @ngdoc function
* @name clientApp.controller:LectureCtrl
* @description
* # LectureCtrl
* Controller of the clientApp
*/
(function() {
  'use strict';
  angular.module('clientApp')
  .controller('LectureCtrl', LectureCtrl);

  /* @ngInject */
  function LectureCtrl($scope, Lecture) {
     //강의 하나 조회
    $scope.lecture = Lecture.get({id:$scope.lectureId}, function() {
      console.log(lecture);
    });

    $scope.lectures = Lecture.query( function() {
      console.log(groups);
    });

    $scope.lecture = new Lecture();
    $scope.lecture.name = "peter";
    lecture.$update(function() {
      //...
    });

  }
}());
