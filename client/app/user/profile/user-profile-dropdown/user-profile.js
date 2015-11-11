angular.module('clientApp')
  .directive('userProfileDropdown', function () {
    return {
      restrict: 'E',
      scope: {
        user: '='
      },
      templateUrl: '/user/profile/user-profile-dropdown/user-profile.html'
    };
  });
