angular.module('clientApp')
  .directive('reply', function () {
    return {
      restrict: 'A',
      scope: {
        reply: '=',
        replies: '='
      },
      templateUrl: '/reply.directive.reply/reply.html',
      /* @ngInject */
      controller: function (user, $scope, replyBroker, confirm) {
        $scope.user = user;
        $scope.modify = function () {
          if ($scope.mod) {
            replyBroker.save({body: $scope.reply.body, id: $scope.reply.id}).then(function(){
              $scope.mod = false;
            });
            return;
          }
          $scope.mod = true;
        };
        $scope.delete = function (id) {
          if (!confirm("삭제하시겠습니까?"))
            return;
          replyBroker.remove(id).then(function () {
            $scope.replies.remove($scope.reply);
          });
        };
      }
    };
  });
