angular.module('clientApp')
  .directive('replies', function () {
    return {
      restrict: 'E',
      scope: {
        contentId:'=',
        replies: '='
      },
      templateUrl: '/reply.directive.replies/replies.html',
      /* @ngInject */
      controller: function (rootUser, $scope, Reply) {
        $scope.reply = new Reply();
        $scope.rootUser = rootUser;
        $scope.writeReply = function (reply) {
          reply.contentId = $scope.contentId;
          reply.save().then(function (result) {
            reply.id = result.id;
            $scope.replies.push(reply);
            $scope.reply = new Reply();
            $scope.reply.contentId = $scope.contentId;
          });
        };
      }
    };
  });
