angular.module('clientApp')
  .directive('contentTemplate', function () {
    return {
      restrict: 'E',
      templateUrl: '/content.directive.content-template/content-template.html'
    };
  });
