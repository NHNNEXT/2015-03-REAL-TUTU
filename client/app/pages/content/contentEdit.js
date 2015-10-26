angular.module('clientApp').config(function ($stateProvider) {
  var controller = function ($stateParams, $scope, http) {
    $scope.types = ['article', 'qna', 'homework'];
    $scope.names = {article: "게시물", qna: "Q & A", homework: "과제"};
    $scope.$watch(function () {
      return $stateParams.id;
    }, function () {
      http.post('/api/v1/article', {id: $stateParams.id}, function () {

      });
    });
  };
  $stateProvider
    .state('contentEdit', {
      url: "/content/:id/edit",
      templateUrl: "/pages/content/contentEdit.html",
      controller: controller
    })
    .state('contentNew', {
      url: "/content/write",
      templateUrl: "/pages/content/contentEdit.html",
      controller: controller
    });
});
