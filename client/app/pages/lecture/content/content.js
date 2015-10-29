angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture.content', {
      url: "/content/:id",
      templateUrl: "/pages/lecture/content/content.html",
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


function merge(arr1, arr2) {
  var result = [], i1 = 0, i2 = 0;
  while (arr1[i1] !== undefined || arr2[i2] !== undefined) {
    if (arr1[i1] === undefined) {
      arr2push();
      continue;
    }
    if (arr2[i2] === undefined) {
      arr1push();
      continue;
    }
    if (arr1[i1] > arr2[i2]) {
      arr2push();
      continue;
    }
    arr1push();
  }
  return result;

  function arr1push() {
    result.push(arr1[i1]);
    i1++;
  }

  function arr2push() {
    result.push(arr2[i2]);
    i2++;
  }
}
