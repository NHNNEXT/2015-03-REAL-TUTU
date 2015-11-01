(function() {
  'use strict';
  angular
    .module('clientApp')
    .controller('CreateLectureCtrl',CreateLectureCtrl);

    /* @ngInject */
    function CreateLectureCtrl($scope,$uibModalInstance,lecture,$log) {
      $scope.create = create;
      $scope.cancel = cancel;
      _init();

      function _init() {
        $scope.lecture = {
          name : "",
          prof : "",
          term : "",
          section : "",
          score: "",
          day :"",
          time : "",
          body : ""
        };
      }
      function create() {
        lecture
        .create($scope.lecture)
        .then(function(response) {
          $uibModalInstance.close(response.data);
        }, function (error) {
            $log.error('error: ' ,error);
        });
      }

      function cancel() {
        $uibModalInstance.dismiss('cancel');
      }
    }
}());
