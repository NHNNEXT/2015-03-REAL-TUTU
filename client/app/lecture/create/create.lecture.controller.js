(function () {
  'use strict';
  angular
    .module('clientApp')
    .controller('CreateLectureController', CreateLectureController);

  /* @ngInject */
  function CreateLectureController($scope, $uibModalInstance, lectureBroker, $log, user) {
    $scope.create = create;
    $scope.cancel = cancel;
    _init();
    $scope.user = user;
    $scope.managers = [];

    $scope.push = function (selected) {
      if (!selected || !selected.email)
        return;
      if (!$scope.managers.includes(selected)) {
        $scope.managers.push(selected);
      }
    };

    function _init() {
      $scope.lecture = {
        name: "",
        term: "",
        type: "",
        score: "",
        day: "",
        time: "",
        body: ""
      };
    }

    function create(lecture){
      var managerIds = [];
      $scope.managers.forEach(function(manager){managerIds.push(manager.id)});
      lecture.managerIds = managerIds;
      lectureBroker.create(lecture);
    }

    function cancel() {
      $uibModalInstance.dismiss('cancel');
    }
  }
}());
