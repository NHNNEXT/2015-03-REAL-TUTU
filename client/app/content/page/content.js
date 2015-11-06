angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('content', {
      url: "/content/:id",
      templateUrl: "/content/page/content.html",
      controller: function ($scope, user, contentBroker, $stateParams, types, replyBroker, $state) {

        $scope.user = user;
        $scope.content = {replies: []};
        $scope.contentTypes = types.contentTypes;

        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          contentBroker.findById(id).then(function (result) {
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

        $scope.delete = function (content) {
          if (!confirm("삭제하시겠습니까?"))
            return;
          contentBroker.delete(content.id).then(function () {
            $state.go('lecture', {id: content.lectureId});
          });
        };


        $scope.writeReply = function (reply) {
          reply.contentId = $scope.content.id;
          replyBroker.save(reply).then(function (result) {
            $scope.content.replies.push(result);
            $scope.reply = {};
          });
        };

      }
    });
});
