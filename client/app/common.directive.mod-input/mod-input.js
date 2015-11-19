angular.module('clientApp').directive('modInput', function ($compile) {
  return {
    restrict: 'E',
    templateUrl: '/common.directive.mod-input/mod-input.html',
    scope: {
      ngModel: '=',
      placeholder: '@',
      maxlength: '@',
      modRight: '=',
      modSave: '='
    },
    /* @ngInject */
    controller: function ($scope) {
      $scope.save = function () {
        $scope.mod = false;
        if (typeof $scope.modSave !== "function")
          return;
        $scope.modSave();
      };
    },
    link: function (s, e, a) {
      var tag = a.tag === undefined ? "span" : a.tag;
      var el = "<span ng-show=\"!mod\">" + "<" + tag + "> <span ng-bind=\"ngModel || placeholder\"></span>" +
        " <small><i ng-show=\"modRight\" ng-click=\"mod=true\"" +
        "class=\"fa fa-pencil\"></i></small></span>" + "</ " + tag + ">" ;
      var newDirective = angular.element(el);
      e.prepend(newDirective);
      $compile(newDirective)(s);
    }
  };
});
