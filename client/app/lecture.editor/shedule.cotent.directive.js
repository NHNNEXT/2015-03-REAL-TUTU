
angular.module('clientApp')
  .directive('scheduleContentList', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '=',
        keyword: '='
      },
      templateUrl: '/lecture.editor/schedule-content-list.html',
      controller: "contentListController"
    };
  });
