angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('homework', {
      url: "/homework/:id",
      templateUrl: "/pages/homework/homework.html",
      controller: function ($stateParams, $scope, http) {

        $scope.$watch(function () {
          return $stateParams.id;
        }, function () {
          http.post('/api/v1/homework', {id: $stateParams.id}, function () {

          });
        });

        $scope.submits = [1, 2, 3, 4, 5];

      }
    });
});
