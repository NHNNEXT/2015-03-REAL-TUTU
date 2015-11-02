(function () {
  'use strict';
  angular
    .module('clientApp')
    .controller('CreateLectureController', CreateLectureController);

  /* @ngInject */
  function CreateLectureController($scope, $uibModalInstance, lecture, $log) {
    $scope.create = create;
    $scope.cancel = cancel;
    _init();
    $scope.push = function (selected) {
      if (!$scope.lecture.managers.includes(selected))
        $scope.lecture.managers.push(selected);
    };

    function _init() {
      $scope.lecture = {
        name: "",
        managers: [],
        term: "",
        type: "",
        score: "",
        day: "",
        time: "",
        body: ""
      };
    }

    function create() {
      lecture
        .create($scope.lecture)
        .then(function (response) {
          $uibModalInstance.close(response.data);
        }, function (error) {
          $log.error('error: ', error);
        });
    }

    function cancel() {
      $uibModalInstance.dismiss('cancel');
    }
  }
}());
