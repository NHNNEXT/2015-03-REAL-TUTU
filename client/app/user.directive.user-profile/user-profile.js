angular.module('clientApp')
  .directive('userProfile', function () {
    return {
      restrict: 'E',
      scope: {
        user: '='
      },
      templateUrl: '/user.directive.user-profile/user-profile.html'
    };
  });
