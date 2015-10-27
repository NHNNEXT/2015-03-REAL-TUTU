angular.module('clientApp')
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '='
      },
      templateUrl: '/directives/content-list/content-list.html',
      controller: function ($scope) {


      }
    };
  });
