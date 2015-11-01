(function () {
  'use strict';

  angular
    .module('clientApp')
    .controller('LectureController', LectureController);

  /* @ngInject */
  function LectureController($scope, $log, lecture, modal) {
    var vm = $scope;
    vm.createLecture = createLecture;
    _init();

    function _init() {
      // change it to $watch
      //_lectures(true);
      //_lectures(false);
    }

    function _lectures(isMyLecture, params) {
      lecture
        .getLectures(isMyLecture, params)
        .then(function (response) {
          $log.info('lecture list: ', response.data);
          if (isMyLecture) {
            vm.myLectures = response.data;
          } else {
            vm.otherLectures = response.data;
          }
        });
    }

    function createLecture() {
      modal
        .open('lg', 'create-lecture.html', 'CreateLectureController')
        .then(function (result) {
          $log.info('create lecture result: ', result);
          vm.myLectures.unshift(result);
        }, function (error) {
        });
    }


    $scope.$watch(function () {
      return vm.myLectureName;
    }, function (newVal, oldVal) {
      _lectures(true, {name: vm.myLectureName});
    });

    $scope.$watch(function () {
      return vm.otherLectureName;
    }, function (newVal, oldVal) {
      _lectures(false, {name: vm.otherLectureName});
    });
  }

}());
