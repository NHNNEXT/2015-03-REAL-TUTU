angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profile', {
      url: "/profile/:id",
      templateUrl: "/pages/user/profile.html",
      controller: function ($scope, user, $stateParams) {
        $scope.user = user;
        $scope.params = $stateParams;
      }
    });
});
