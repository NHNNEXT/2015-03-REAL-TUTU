angular.module('clientApp')
  .directive('userName', function () {
    return {
      restrict: 'E',
      scope: {
        user: '='
      },
      templateUrl: '/user.directive.user-name/user-name.html'
    };
  });
