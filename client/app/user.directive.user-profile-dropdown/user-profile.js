angular.module('clientApp')
  .directive('userProfileDropdown', function () {
    return {
      restrict: 'E',
      scope: {
        user: '='
      },
      templateUrl: '/user.directive.user-profile-dropdown/user-profile.html'
    };
  });
