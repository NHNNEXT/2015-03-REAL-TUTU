
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
     var lecture = new Lecture();
     lecture.load(1).then(function(lectureData) {
       $scope.lecture = lectureData;
     });
  }
}());
