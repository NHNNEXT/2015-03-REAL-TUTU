(function () {
  'use strict';

  angular
    .module('clientApp')
    .controller('LectureController', LectureController);

  /* @ngInject */
  function LectureController($scope, lectureBroker, modal) {
    _init();

    function _init() {
      lectureBroker.getList(function (lectures) {
        $scope.lectures = lectures;
      });
    }

    //function _lectures(isMyLecture, params) {
    //  lecture
    //    .getLectures(isMyLecture, params)
    //    .then(function (response) {
    //      $log.info('lecture list: ', response.data);
    //      if (isMyLecture) {
    //        vm.myLectures = response.data;
    //      } else {
    //        vm.otherLectures = response.data;
    //      }
    //    });
    //}

    //
    //$scope.$watch(function () {
    //  return vm.myLectureName;
    //}, function (newVal, oldVal) {
    //  _lectures(true, {name: vm.myLectureName});
    //});
    //
    //$scope.$watch(function () {
    //  return vm.otherLectureName;
    //}, function (newVal, oldVal) {
    //  _lectures(false, {name: vm.otherLectureName});
    //});
  }

}());

