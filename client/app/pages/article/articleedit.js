angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('articleedit', {
      url: "/article/edit/:id",
      templateUrl: "/pages/article/articleedit.html",
      controller: function ($stateParams, $scope, http) {

        $scope.$watch(function () {
          return $stateParams.id;
        }, function () {
          http.post('/api/v1/article', {id: $stateParams.id}, function () {

          });
        });

      }
    });
});
