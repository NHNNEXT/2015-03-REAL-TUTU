angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('article', {
      url: "/article/:id",
      templateUrl: "/pages/article/article.html",
      controller: function ($scope) {


        $scope.article = {title: "제목", body: "내용", writer:"작성자", date:new Date()}

      }
    });
});
