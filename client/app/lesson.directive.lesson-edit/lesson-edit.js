angular.module('clientApp')
  .directive('lessonEdit', function () {
    return {
      restrict: 'E',
      scope: {
        lesson: '=',
        index: '=',
        lessons: '='
      },
      templateUrl: '/lesson.directive.lesson-edit/lesson-edit.html'
    };
  });
