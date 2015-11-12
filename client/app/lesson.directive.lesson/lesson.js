angular.module('clientApp')
  /* @ngInject */
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
