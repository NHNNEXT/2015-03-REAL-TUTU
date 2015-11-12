angular.module('clientApp')
  .directive('dropDown', function () {
    return {
      restrict: 'E',
      scope: {
        dropClass: '@',
        options: '=',
        data: '=',
        ngModel: '=',
        placeholder: '@',
        names: '='
      },
      templateUrl: '/common.directive.drop-down/drop-down.html',
      /* @ngInject */
      controller: function ($scope) {
        $scope.setSelected = function (option) {
          $scope.ngModel = option;
        };
      },
      link: function (s, e, a) {
        var distinct = a.distinct;
        if (!distinct)
          return;
        if (!s.data)
          return;
        if (!s.data.forEach)
          return;
        s.options = [];
        angular.forEach(s.data, function (datum) {
          if (!s.options.includes(datum[distinct]))
            s.options.push(datum[distinct]);
        });
      }
    };
  });
