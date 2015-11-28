angular.module('clientApp')
  .directive('replies', function () {
    return {
      restrict: 'E',
      scope: {
        contentId: '='
      },
      templateUrl: '/reply.directive.replies/replies.html',
      /* @ngInject */
      controller: function (rootUser, $scope, Reply) {
        $scope.replies = [];
        $scope.more = function () {
          var query = {};
          query.contentId = $scope.contentId;
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
        $scope.$watch('contentId', function (id) {
          if (!id)
            return;
          $scope.more();
        });

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
