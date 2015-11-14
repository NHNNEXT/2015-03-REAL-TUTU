angular.module('clientApp')
  .directive('myProfile', function () {
    return {
      restrict: 'E',
      templateUrl: '/user.directive.my-profile/my-profile.html',
      /* @ngInject */
      controller: function ($scope, rootUser) {
        $scope.logout = rootUser.logout;
      }
    };
  });
