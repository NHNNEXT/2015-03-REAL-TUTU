'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:ArticleCtrl
 * @description
 * # ArticleCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('ArticleCtrl', function ($scope, $stateParams, http) {
    $scope.article = {};
    $scope.$watch(function () {
      return $stateParams.id;
    }, function () {
      http.post('/api/v1/article', {id: $stateParams.id}, function (result) {
        angular.copy($scope.article, result);
      });
    });

  });
