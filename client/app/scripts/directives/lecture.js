'use strict';

/**
 * @ngdoc directive
 * @name clientApp.directive:lecture
 * @description
 * # lecture
 */
angular.module('clientApp')
  .directive('lecture', function () {
    return {
      templateUrl: '/views/directives/lecture.html',
      restrict: 'A',
      scope: {
        lecture: '='
      },
      link: function postLink() {
        //element.text('this is the lecture directive');
      }
    };
  });
