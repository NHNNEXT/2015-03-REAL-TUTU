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
      controller: function (rootUser, $scope, confirm) {
        $scope.rootUser = rootUser;
        $scope.modify = function () {
          if ($scope.mod) {
            $scope.reply.save().then(function () {
              $scope.mod = false;
            });
            return;
          }
          $scope.mod = true;
        };
        $scope.delete = function () {
          confirm("삭제하시겠습니까?", undefined, function () {
            $scope.reply.remove().then(function () {
              $scope.replies.remove($scope.reply);
            });
          });
        };

      }
    };
  });
