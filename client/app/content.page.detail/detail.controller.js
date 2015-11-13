angular.module('clientApp').controller('contentDetailController',
  /* @ngInject */
function ($scope, rootUser, contentBroker, $stateParams, types, replyBroker, $state, $sce, confirm) {
  $scope.rootUser = rootUser;
  $scope.content = {replies: []};
  $scope.contentTypes = types.contentTypes;

  $scope.body = function () {
    return $sce.trustAsHtml($scope.content.body);
  };

  $scope.$watch(function () {
    return $stateParams.id;
  }, function (id) {
    contentBroker.findById(id).then(function (result) {
      angular.copy(result, $scope.content);
      $scope.content.dueDate = new Date($scope.content.dueDate);
      $scope.content.writeDate = new Date($scope.content.writeDate);
    });
  });


  $scope.delete = function (content) {
    if (!confirm("삭제하시겠습니까?"))
      return;
    contentBroker.remove(content.id).then(function () {
      $state.go('lecture', {id: content.lectureId});
    });
  };

  $scope.writeReply = function (reply) {
    reply.contentId = $stateParams.id;
    replyBroker.save(reply).then(function (result) {
      $scope.content.replies.push(result);
      $scope.reply = {};
    });
  };
});
