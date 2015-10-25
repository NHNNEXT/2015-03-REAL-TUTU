angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('articleBoard', {
      url: "/article/board/:id",
      templateUrl: "/pages/article/board.html",
      controller: function ($scope) {
        $scope.articles = [
          {writer: "황정민", title: "제목", hits: 10},
          {writer: "황정닌", title: "제목1", hits: 10},
          {writer: "황정딘", title: "제목2", hits: 13},
          {writer: "황정긴", title: "제목3", hits: 14},
          {writer: "황정ㅇ", title: "제목4", hits: 15},
        ];
      }
    });
});
