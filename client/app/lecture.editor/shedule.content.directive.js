
angular.module('clientApp')
  .directive('scheduleContentList', function () {
    return {
      restrict: 'E',
      templateUrl: '/lecture.editor/schedule-content-list.html'
    };
  });
