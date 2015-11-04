angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profile', {
      url: "/profile/:id",
      templateUrl: "/user/page/profile.html",
      controller: function ($scope, user, $stateParams, userBroker) {

        $scope.update = function () {
          userBroker.update($scope.user);
        };

        $scope.isRootUser = function () {
          return user.id === $scope.user.id;
        };
        $scope.user = {};
        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          if (!id)
            return;
          userBroker.findById(id, function (result) {
            angular.copy(result, $scope.user);
          })
        });
      }
    });
});
