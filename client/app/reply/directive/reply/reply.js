angular.module('clientApp')
  .directive('reply', function () {
    return {
      restrict: 'A',
      scope: {
        reply: '=',
        replies: '='
      },
      templateUrl: '/reply/directive/reply/reply.html',
      controller: function (user, $scope, replyBroker) {
        $scope.user = user;
        $scope.save = function () {
          replyBroker.save({body: $scope.reply.body, id: $scope.reply.id});
        };
        $scope.delete = function (id) {
          replyBroker.deleteReply(id).then(function () {
            if (!confirm("삭제하시겠습니까?"))
              return;
            $scope.replies.remove($scope.reply);
          });
        };
      }
    }
  });
