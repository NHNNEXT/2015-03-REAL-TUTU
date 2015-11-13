angular.module('clientApp').controller('contentEditController',
  /* @ngInject */
  function ($stateParams, $scope, contentBroker, types, rootUser, $state) {
    $scope.rootUser = rootUser;

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id) {
        if (!rootUser.currentLecture) {
          $state.go('mylectures');
          return;
        }
        $scope.types = rootUser.currentLecture.types;

        $scope.content = {type: 0, lectureId: rootUser.currentLecture.id, lectureName: rootUser.currentLecture.name};
        return;
      }
      contentBroker.findById(id).then(function (result) {
        $scope.content = {};
        angular.copy(result, $scope.content);
        if ($scope.content.dueDate)
          $scope.content.dueDate = new Date($scope.content.dueDate);
        $scope.content.writeDate = new Date($scope.content.writeDate);
        $scope.types = result.types;
      });
    });

    $scope.edit = function (content) {
      contentBroker.edit(content).then(function (response) {
        var id = content.id === undefined ? response.id : content.id;
        $state.go('content', {id: id});
      });
    };
  });
