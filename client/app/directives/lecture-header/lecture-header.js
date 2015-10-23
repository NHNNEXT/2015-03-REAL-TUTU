angular.module('clientApp')
  .directive('lectureHeader', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        lecture: '='
      },
      templateUrl: '/directives/lecture-header/lecture-header.html'
    };
  });
