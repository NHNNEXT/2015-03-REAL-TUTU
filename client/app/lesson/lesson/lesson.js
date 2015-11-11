angular.module('clientApp')
  .directive('lesson', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        lesson: '=',
        index: '='
      },
      templateUrl: '/lesson/lesson/lesson.html'
    };
  });
