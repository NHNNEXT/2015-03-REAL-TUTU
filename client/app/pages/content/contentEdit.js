angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('contentEdit', {
      url: "/content/edit/1",
      templateUrl: "/pages/content/contentEdit.html",
      controller: function ($stateParams, $scope, http) {

        $scope.types = ["숙제", "게시물"];

        $scope.$watch(function () {
          return $stateParams.id;
        }, function () {
          http.post('/api/v1/article', {id: $stateParams.id}, function () {

          });
        });

      }
    });
});
