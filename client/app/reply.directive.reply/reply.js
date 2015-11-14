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
      controller: function (rootUser, $scope, replyBroker, confirm) {
        $scope.rootUser = rootUser;
        $scope.modify = function () {
          if ($scope.mod) {
            $scope.reply.save();
            return;
          }
          $scope.mod = true;
        };
        $scope.delete = function (id) {
          if (!confirm("삭제하시겠습니까?"))
            return;
          $scope.reply.remove().then(function () {
            $scope.replies.remove($scope.reply);
          });
        };
      }
    };
  });
