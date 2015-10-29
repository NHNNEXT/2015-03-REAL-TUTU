angular.module('clientApp').config(function ($stateProvider) {
  var controller = function ($stateParams, $scope, http) {
    $scope.types = ['article', 'qna', 'homework'];
    $scope.names = {article: "게시물", qna: "Q & A", homework: "과제"};
    $scope.content = {type: $stateParams.type};
    if ($scope.content.type === undefined) {
      $scope.content.type = 'article';
    }

    $scope.addTag = function (tag) {
      if (!$scope.content.tags)
        $scope.content.tags = [];
      if ($scope.content.tags.includes(tag))
        return;
      $scope.content.tags.push(tag);
      $scope.tag = "";
    };

    $scope.$watch(function () {
      return $stateParams.id;
    }, function () {
      http.post('/api/v1/article', {id: $stateParams.id}, function () {

      });
    });
  };
  $stateProvider
    .state('lecture.contentEdit', {
      url: "/content/:id/edit?:type",
      templateUrl: "/pages/lecture/content/contentEdit.html",
      controller: controller
    })
    .state('lecture.contentNew', {
      url: "/content/write?:type",
      templateUrl: "/pages/lecture/content/contentEdit.html",
      controller: controller
    });
});
