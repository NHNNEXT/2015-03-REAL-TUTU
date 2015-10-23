angular.module('clientApp')
  .directive('userProfile', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        userId: '=',
        user: '='
      },
      templateUrl: '/directives/lecture-profile/lecture-profile.html'
    };
  });
