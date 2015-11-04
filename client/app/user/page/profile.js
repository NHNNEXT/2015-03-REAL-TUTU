angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profile', {
      url: "/profile/:id",
      templateUrl: "/user/page/profile.html",
      controller: function ($scope, user, $stateParams, userBroker) {
        $scope.rootUser = user;
        $scope.user = {};
        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          userBroker.findById(id, function (result) {
            angular.copy(result, $scope.user);
          })
        });
      }
    });
});
