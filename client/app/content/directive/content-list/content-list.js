angular.module('clientApp')
  .directive('contentList', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '='
      },
      templateUrl: '/content/directive/content-list/content-list.html',
      controller: function ($scope) {


      }
    };
  });
