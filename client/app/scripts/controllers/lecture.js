'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:LectureCtrl
 * @description
 * # LectureCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('LectureCtrl', function ($stateParams, $scope, http) {
    $scope.lecture = {};
    $scope.$watch(function () {
      return $stateParams.id;
    }, function () {
      http.post('/api/v1/subject', {id: $stateParams.id}, function (result) {
        angular.copy($scope.lecture, result);
      });
    });
  });
