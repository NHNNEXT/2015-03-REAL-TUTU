(function () {
  'use strict';

  angular
    .module('clientApp')
    .controller('LectureController', LectureController)
    .config(config);

  /* @ngInject */
  function LectureController($scope, lectureBroker) {
    _init();
    function _init() {
      lectureBroker.getList().then(function (lectures) {
        $scope.lectures = lectures;
      });
    }
  }


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
