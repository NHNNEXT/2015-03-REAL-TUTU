angular.module('clientApp').controller('contentDetailController',
  /* @ngInject */
function ($scope, rootUser, Content, $stateParams, types, Reply, $state) {
  $scope.rootUser = rootUser;
  $scope.content = {replies: []};
  $scope.contentTypes = types.contentTypes;

  $scope.$watch(function () {
    return $stateParams.id;
  }, function (id) {
    Content.findById(id).then(function(content){
      $scope.content = content;
      $state.current.header = content.lectureName;
    });
    $scope.reply = new Reply();
    $scope.reply.contentId = id;
  });

  $scope.writeReply = function (reply) {
    reply.contentId = $stateParams.id;
    reply.save().then(function (result) {
      reply.id = result.id;
      $scope.content.replies.push(reply);
      $scope.reply = new Reply();
      $scope.reply.contentId = $scope.content.id;
    });
  };
});
