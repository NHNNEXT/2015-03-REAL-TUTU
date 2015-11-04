angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture.content', {
      url: "/content/:id",
      templateUrl: "/content/page/content.html",
      controller: function ($scope, user) {

        $scope.user = user;

        $scope.remainDay = function (date) {
          return parseInt((new Date() - date) / 1000 / 60 / 60 / 24 * 10) / 10;
        };


        $scope.content = {
          type: 'homework',
          title: "제목",
          body: "내용",
          writer: "작성자",
          date: new Date(),
          dueDate: new Date(2015, 9, 26)
        };

        $scope.replies = [];

        $scope.writeReply = function (reply) {
          reply.writer = user;
          $scope.replies.push(reply);
          $scope.reply = {};
        };

      }
    });
});