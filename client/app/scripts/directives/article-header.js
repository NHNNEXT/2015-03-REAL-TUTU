'use strict';

/**
 * @ngdoc directive
 * @name clientApp.directive:articleHeader
 * @description
 * # articleHeader
 */
angular.module('clientApp')
  .directive('articleHeader', function () {
    return {
      templateUrl: '/views/directives/article-header.html',
      restrict: 'E',
      scope: {
        article: '='
      }
    };
  });
