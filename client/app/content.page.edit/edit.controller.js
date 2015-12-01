angular.module('clientApp').controller('contentEditController',
  /* @ngInject */
  function ($stateParams, $scope, Content, types, rootUser, $state) {
    $scope.rootUser = rootUser;

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (id === undefined)
        return;
      Content.findById(id).then(function (content) {
        $scope.content = content;
        $state.current.header = content.lectureName;
        if ($scope.content.contentGroup.contentType.startTime)
          $scope.placeholder = "끝";
        else
          $scope.placeholder = "마감";
      });
    });

    $scope.edit = function (content) {
      content.save().then(function (response) {
        var id = content.id === undefined ? response.id : content.id;
        $state.go('content', {id: id});
      });
    };


  });
