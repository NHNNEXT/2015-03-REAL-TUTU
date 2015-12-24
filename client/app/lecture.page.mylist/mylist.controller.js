angular.module('clientApp').controller('myListController',
  /* @ngInject */
  function ($scope, rootUser, types, http) {
    $scope.rootUser = rootUser;
    $scope.majorTypes = types.majorTypes;
    $scope.lectures = [];
    $scope.sideToggleRequest = function (lecture) {
      http.post('/api/v1/lecture/sideMenu', {lectureId: lecture.id}).then(function (result) {
        lecture.sideMenu = result;
      });
    };
  });
