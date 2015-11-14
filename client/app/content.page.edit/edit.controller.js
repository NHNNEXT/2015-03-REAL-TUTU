angular.module('clientApp').controller('contentEditController',
  /* @ngInject */
  function ($stateParams, $scope, Content, types, rootUser, $state) {
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

        $scope.content = new Content({type: 0, lectureId: rootUser.currentLecture.id, lectureName: rootUser.currentLecture.name});
        return;
      }
      $scope.content = new Content(id);
    });

    $scope.edit = function (content) {
      content.save().then(function (response) {
        var id = content.id === undefined ? response.id : content.id;
        $state.go('content', {id: id});
      });
    };
  });
