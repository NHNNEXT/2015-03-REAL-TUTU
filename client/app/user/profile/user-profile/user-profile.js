angular.module('clientApp')
  .directive('userProfile', function () {
    return {
      restrict: 'E',
      scope: {
        user: '='
      },
      templateUrl: '/user/profile/user-profile/user-profile.html'
    };
  });
