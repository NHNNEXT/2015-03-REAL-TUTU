angular.module('clientApp').controller('contentEditController',
  /* @ngInject */
  function ($stateParams, $scope, Content, types, rootUser, $state, Lecture, User, pageMove) {
    $scope.rootUser = rootUser;

    $scope.toggleAll = function () {
      $scope.content.users.forEach(function (user) {
        user.submit = !user.submit;
      });
    };

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (id === undefined)
        return;
      Content.findById(id).then(function (content) {
        $scope.content = content;
        $state.current.header = content.lectureName;
        if (content.contentGroup.contentType !== "SUBMIT")
          return;
        Lecture.getWriteInfoById(content.lectureId).then(function (writeInfo) {
          $scope.content.users = [];
          $scope.hostUserId = writeInfo.hostUserId;
          writeInfo.users.forEach(function (user) {
            var u = new User(user);
            $scope.content.users.push(u);
            u.submit = undefined !== content.submitRequires.find(function (submitRequire) {
                return submitRequire.user.id === u.id;
              });
          });
        });
      });
    });

    $scope.edit = function (content) {
      content.body = $('[froala]').froalaEditor('html.get');
      content.save().then(function () {
        pageMove.ok = true;
        $state.go('content', {id: content.id});
      });
    };

  });
