angular.module('clientApp').config(function ($stateProvider) {
  var controller = function ($stateParams, $scope, contentBroker, types) {
    $scope.content = {type: $stateParams.type};
    $scope.contentTypes = types.contentTypes;
    if ($scope.content.type === undefined) {
      $scope.content.type = 'article';
    }

    $scope.create = contentBroker.create;

    $scope.$watch(function () {
      return $stateParams.id;
    }, function () {
    });
  };
  $stateProvider
    .state('contentEdit', {
      url: "/content/:id/edit?:type?:lectureId",
      templateUrl: "/content/page/contentEdit.html",
      controller: controller
    })
    .state('contentNew', {
      url: "/content/write?:type?:lectureId",
      templateUrl: "/content/page/contentEdit.html",
      controller: controller
    });
});
