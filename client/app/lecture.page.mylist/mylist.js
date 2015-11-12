angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('mylectures', {
      url: "/mylectures",
      templateUrl: "/lecture.page.mylist/mylist.html",
      controller: function ($scope, user) {
        $scope.user = user;

      }
    });
});
