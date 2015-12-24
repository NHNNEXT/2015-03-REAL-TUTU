(function () {
  'use strict';

  angular
    .module('clientApp')
    .controller('LectureController', LectureController)

  /* @ngInject */
  function LectureController($scope, Lecture) {
    _init();
    function _init() {
      Lecture.getList().then(function (lectures) {
        $scope.lectures = lectures;
      });
    }
  }

}());
