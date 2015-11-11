angular.module('clientApp')
  .directive('myProfile', function () {
    return {
      restrict: 'E',
      templateUrl: '/user/profile/my-profile/my-profile.html',
      controller: function (user, $scope, userBroker) {
        $scope.logout = userBroker.logout;
      }
    };
  });
