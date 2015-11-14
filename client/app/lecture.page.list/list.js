(function () {
  'use strict';

  angular
    .module('clientApp')
    .controller('LectureController', LectureController)
    .config(config);

  /* @ngInject */
  function LectureController($scope, Lecture) {
    _init();
    function _init() {
      Lecture.getList().then(function (lectures) {
        $scope.lectures = lectures;
      });
    }
  }

  /* @ngInject */
  function config($stateProvider) {
    $stateProvider
      .state('lectures', {
        url: "/lectures",
        templateUrl: "/lecture.page.list/list.html",
        controller: "LectureController",
        controllerAS: "lecture"
      });
  }
}());
