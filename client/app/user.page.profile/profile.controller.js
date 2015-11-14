angular.module('clientApp')
  /* @ngInject */
  .controller('profileController', function ($scope, rootUser, $stateParams, userBroker, alert, User) {

    $scope.uploadCallback = uploadCallback;
    $scope.update = update;
    $scope.isRootUser = isRootUser;

    function uploadCallback(resp) {
      $scope.user.profileUrl = "/uploads/" + resp.data.result;
      update();
    }

    function isRootUser() {
      if (!rootUser.isLogged())
        return;
      return rootUser.id === $scope.user.id;
    }

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id)
        return;
      User.findById(id).then(function (user) {
        $scope.user = user;
      });
    });
  });
