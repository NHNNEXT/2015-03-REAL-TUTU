angular.module('clientApp')
  .directive('userProfile', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        user: '='
      },
      templateUrl: '/directives/user-profile/user-profile.html'
    };
  });
