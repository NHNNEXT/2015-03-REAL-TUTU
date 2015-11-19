angular.module('clientApp')
  /* @ngInject */
  .controller('profileController', function ($scope, rootUser, $stateParams, alert, User, $state) {

    $scope.uploadCallback = uploadCallback;

    function uploadCallback(resp) {
      $scope.user.profileUrl = resp.data.link;
      $scope.update();
    }

    $scope.update = function () {
      rootUser.update($scope.user);
    };

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id)
        return;
      User.findById(id).then(function (user) {
        $scope.user = user;
        $state.current.header = user.name;
      });
    });
  });
