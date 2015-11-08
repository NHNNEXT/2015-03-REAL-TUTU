angular.module('clientApp')
  .directive('contentHeader', function () {
    return {
      restrict: 'A',
      scope: {
        content: '='
      },
      templateUrl: '/content/directive/content-header/content-header.html',
      controller: function ($scope, types) {
          $scope.types = types;
      }
    };
  });
