angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('mylectures', {
      url: "/mylectures",
      templateUrl: "/lecture/view/mylist/mylist.html",
      controller: function ($scope, user) {
        $scope.user = user;

      }
    });
});
