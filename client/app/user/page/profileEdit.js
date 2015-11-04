angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profileEdit', {
      url: "/profile/:id/edit",
      templateUrl: "/user/page/profileEdit.html",
      controller: function ($scope, user, $stateParams, userBroker) {
        $scope.user = user;
        $scope.params = $stateParams;
        $scope.update = userBroker.update;

      }
    });
});
