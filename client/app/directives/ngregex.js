'use strict';

/**
 * @ngdoc directive
 * @name clientApp.directive:ngRegex
 * @description
 * # ngRegex
 */
angular.module('clientApp')
  .directive('ngRegex', function ($compile) {
    return {
      restrict: 'A',
      scope: {ngModel: '='},
      link: function (scope, element, attrs) {
        var message = angular.element("<span class='message' ng-show='!matched && ngModel!=undefined && ngModel.length != 0'>" + attrs.message + '</span>');
        $compile(message)(scope);

        element[0].parentNode.insertBefore(message[0], element[0].nextSibling);
        var parent = scope.$parent;
        var regex = parent.$eval(attrs.ngRegex);

        if (regex === undefined)
          regex = new RegExp(attrs.ngRegex);

        var regexTest = function () {
          return regex.test(parent.$eval(attrs.ngModel));
        };

        parent.$watch(attrs.ngModel, function () {
          if (parent.$eval(attrs.ngModel) === undefined || parent.$eval(attrs.ngModel) === "") {
            setRegex(false, true);
            return;
          }
          if (regexTest()) {
            setRegex(true);
            return;
          }
          setRegex(false);
          function setRegex(val, not) {
            scope.matched = val;
            if (!val && !not)
              element.addClass('waring');
            else
              element.removeClass('waring');

            if (parent.ngRegex === undefined)
              parent.ngRegex = {};
            parent.ngRegex[attrs.ngModel] = val;
          }
        });
      }
    };
  });
