angular.module('clientApp').controller('contentDetailController',
  /* @ngInject */
function ($scope, rootUser, Content, $stateParams, types, Reply) {
  $scope.rootUser = rootUser;
  $scope.content = {replies: []};
  $scope.contentTypes = types.contentTypes;

  $scope.$watch(function () {
    return $stateParams.id;
  }, function (id) {
    $scope.content = new Content(id);
  });

  $scope.reply = new Reply();
  $scope.writeReply = function (reply) {
    reply.contentId = $stateParams.id;
    reply.save().then(function (result) {
      reply.id = result.id;
      $scope.content.replies.push(reply);
      $scope.reply = new Reply();
    });
  };
});
