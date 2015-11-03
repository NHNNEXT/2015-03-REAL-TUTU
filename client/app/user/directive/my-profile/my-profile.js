angular.module('clientApp')
  .directive('myProfile', function () {
    return {
      restrict: 'E',
      templateUrl: '/user/directive/my-profile/my-profile.html',
      controller: function (user, $scope, userBroker) {
        $scope.user = user;
        $scope.logout = userBroker.logout;
      }
    };
  });
