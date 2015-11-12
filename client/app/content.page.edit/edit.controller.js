angular.module('clientApp').controller('contentEditController',
  function ($stateParams, $scope, contentBroker, types, user, $state) {
    $scope.user = user;

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id) {
        if (!user.currentLecture) {
          $state.go('mylectures');
          return;
        }
        $scope.types = user.currentLecture.types;

        $scope.content = {type: 0, lectureId: user.currentLecture.id, lectureName: user.currentLecture.name};
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
