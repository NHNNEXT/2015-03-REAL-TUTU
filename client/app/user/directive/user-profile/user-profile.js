angular.module('clientApp')
  .directive('userProfile', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        user: '='
      },
      templateUrl: '/user/directive/user-profile/user-profile.html'
    };
  });
