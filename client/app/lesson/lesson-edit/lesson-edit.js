angular.module('clientApp')
  .directive('lessonEdit', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        lesson: '=',
        index: '=',
        lessons: '='
      },
      templateUrl: '/lesson/lesson-edit/lesson-edit.html'
    };
  });
