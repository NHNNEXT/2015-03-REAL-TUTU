angular.module('clientApp')
  .directive('rootUser', function () {
    return {
      restrict: 'E',
      templateUrl: '/user.directive.root-user/root-user.html'
    };
  });
