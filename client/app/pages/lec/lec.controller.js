(function() {
  'use strict';

  angular
    .module('clientApp')
    .controller('LectureCtrl',LectureCtrl);

    /* @ngInject */
    function LectureCtrl($scope, lecture, modal, logger) {
      var vm = this;
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
            logger.info('lecture list: ' , response.data);
            if(isMyLecture) {
              vm.myLectures = response.data;
            } else {
              vm.otherLectures = response.data;
            }
          });
      }

      function createLecture() {
        modal
        .open('sm', 'create-lecture.html', 'CreateLectureCtrl')
        .then(function(result) {
          logger.info('create lecture result: ', result);
          vm.myLectures.unshift(result);
        },function(error) {});
      }


    $scope.$watch(function () {
      return vm.myLectureName;
    }, function(newVal,oldVal) {
      _lectures(true, {name:vm.myLectureName});
    });

    $scope.$watch(function() {
      return vm.otherLectureName;
    }, function(newVal, oldVal) {
      _lectures(false, {name:vm.otherLectureName});
    });
  }

}());
