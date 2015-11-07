'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:HeaderCtrl
 * @description
 * # HeaderCtrl
 * Controller of the clientApp
 */
var modals;
angular.module('clientApp')
  .controller('root', function ($state, $scope, modal) {
    $scope.state = $state;
    modals = modal;
  });
