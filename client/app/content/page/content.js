angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('content', {
      url: "/content/:id",
      templateUrl: "/content/page/content.html",
      controller: function ($scope, user, contentBroker, $stateParams, types) {

        $scope.user = user;
        $scope.content = {replies: []};
        $scope.contentTypes = types.contentTypes;

        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          contentBroker.findById(id, function (result) {
            angular.copy(result, $scope.content);
            $scope.content.dueDate = new Date($scope.content.dueDate);
            $scope.content.writeDate = new Date($scope.content.writeDate);
          });
        });

        $scope.remainDay = function (date) {
          var remain = parseInt((new Date() - date) / 1000 / 60 / 60 / 24 * 10) / 10;
          if (remain < 0)
            return 0;
          return remain;
        };


        $scope.writeReply = function (reply) {
          reply.writer = user;
          $scope.content.replies.push(reply);
          $scope.reply = {};
        };

      }
    });
});
