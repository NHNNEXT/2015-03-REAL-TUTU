angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('mylectures', {
      url: "/mylectures",
      templateUrl: "/lecture/page/mylectures/mylectures.html",
      controller: function ($scope, user) {
        $scope.user = user;

      }
    });
});
