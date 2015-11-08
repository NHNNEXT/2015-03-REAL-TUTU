angular.module('clientApp').config(function ($stateProvider) {
  var controller = function ($stateParams, $scope, contentBroker, types, user, $state) {
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
        angular.copy(result, $scope.content);
        $scope.content.dueDate = new Date($scope.content.dueDate);
        $scope.content.writeDate = new Date($scope.content.writeDate);
        $scope.types = JSON.parse(result.types);
      });
    });

    $scope.edit = function (content) {
      contentBroker.edit(content).then(function () {
        $state.go('lecture', {id: content.lectureId});
      });
    };

  };

  $stateProvider
    .state('contentEdit', {
      url: "/content/:id/edit",
      templateUrl: "/content/page/contentEdit.html",
      controller: controller
    })
    .state('contentNew', {
      url: "/content/write",
      templateUrl: "/content/page/contentEdit.html",
      controller: controller
    });
});
