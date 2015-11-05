angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profile', {
      url: "/profile/:id",
      templateUrl: "/user/page/profile.html",
      controller: function ($scope, user, $stateParams, userBroker) {

        $scope.uploadCallback = uploadCallback;
        $scope.update = update;
        $scope.isRootUser = isRootUser;

        function uploadCallback(resp) {
          $scope.user.profileUrl = "/uploads/" + resp.data.result;
          update();
        }

        function update() {
          userBroker.update($scope.user);
        }

        function isRootUser() {
          return user.id === $scope.user.id;
        }

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
