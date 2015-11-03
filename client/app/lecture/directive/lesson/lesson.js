angular.module('clientApp')
  .directive('lesson', function ($compile, $state) {
    return {
      restrict: 'A',
      scope: {
        lesson: '=',
        index: '='
      },
      templateUrl: '/lecture/directive/lesson/lesson.html',
    };
  });
