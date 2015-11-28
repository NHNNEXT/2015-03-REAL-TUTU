angular.module('clientApp')
  .directive('replies', function () {
    return {
      restrict: 'E',
      scope: {
        content: '='
      },
      templateUrl: '/reply.directive.replies/replies.html',
      /* @ngInject */
      controller: function (rootUser, $scope, Reply) {
        $scope.replies = [];
        $scope.more = function () {
          var query = {};
          query.contentId = $scope.content.id;
          query.page = $scope.page;
          Reply.getList(query).then(function (replies) {
            $scope.page++;
            $scope.moreReplyExist = replies.length === 10;
            replies.forEach(function (reply) {
              $scope.replies.push(reply);
            });
          });
        };

        $scope.page = 0;
        $scope.$watch('content', function (id) {
          if (!id)
            return;
          $scope.more();
        });

        $scope.reply = new Reply();
        $scope.rootUser = rootUser;
        $scope.writeReply = function (reply) {
          reply.contentId = $scope.content.id;
          reply.save().then(function (result) {
            reply.id = result.id;
            $scope.content.repliesSize++;
            $scope.replies.push(reply);
            $scope.reply = new Reply();
            $scope.reply.contentId = $scope.content.id;
          });
        };
      }
    };
  });
