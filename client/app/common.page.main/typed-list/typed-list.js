angular.module('clientApp')
  .directive('typedList', function () {
    return {
      restrict: 'E',
      //scope: {
      //  done: '=',
      //  ngModel: '=',
      //  default: '@'
      //},
      templateUrl: "/common.page.main/typed-list/typed-list.html",
      /* @ngInject */
      controller: function ($scope, Content) {
        Content.getListInMyLectures({contentType:"NOTICE"}).then(function (contents) {
          $scope.contents = contents;
        });
      }
    };
  });
