angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('homeworkedit', {
      url: "/homework/edit/:id",
      templateUrl: "/pages/homework/homeworkedit.html",
      controller: function ($stateParams, $scope, http) {

        $scope.$watch(function () {
          return $stateParams.id;
        }, function () {
          http.post('/api/v1/homework', {id: $stateParams.id}, function () {

          });
        });

      }
    });
});
