angular.module('clientApp').controller('contentDetailController',
  /* @ngInject */
function ($scope, rootUser, Content, $stateParams, types, replyBroker) {
  $scope.rootUser = rootUser;
  $scope.content = {replies: []};
  $scope.contentTypes = types.contentTypes;

  $scope.$watch(function () {
    return $stateParams.id;
  }, function (id) {
    $scope.content = new Content(id);
  });

  $scope.writeReply = function (reply) {
    reply.contentId = $stateParams.id;
    replyBroker.save(reply).then(function (result) {
      $scope.content.replies.push(result);
      $scope.reply = {};
    });
  };
});
