angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('mylectures', {
      url: "/mylectures",
      templateUrl: "/user/page/mylectures/mylectures.html",
      controller: function ($scope, user) {
        $scope.user = user;

      }
    });
});
