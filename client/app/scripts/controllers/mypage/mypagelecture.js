'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:LectureCtrl
 * @description
 * # LectureCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('MyPageLectureCtrl', function ($scope) {
    $scope.lectures = [];

    $scope.lectures.push(new Lecture("자구알", "김동진", "월수 2교시", 1));
    $scope.lectures.push(new Lecture("자구알", "김동진", "월수 2교시", 2));
    $scope.lectures.push(new Lecture("자구알", "김동진", "월수 2교시", 3));

    function Lecture(name, prof, time, id) {
      this.name = name;
      this.prof = prof;
      this.time = time;
      this.id = id;
    }
  });
