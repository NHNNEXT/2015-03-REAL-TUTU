angular.module('clientApp')
  .directive('lesson', function () {
    return {
      restrict: 'E',
      scope: {
        lesson: '=',
        index: '='
      },
      templateUrl: '/lesson.directive.lesson/lesson.html'
    };
  });
