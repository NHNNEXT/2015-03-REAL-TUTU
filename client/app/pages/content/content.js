angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('content', {
      url: "/content/:id",
      templateUrl: "/pages/content/content.html",
      controller: function ($scope) {


        $scope.article = {title: "제목", body: "내용", writer: "작성자", date: new Date()};

        $scope.replies = ["wearawer", "123123"];

      }
    });
});
