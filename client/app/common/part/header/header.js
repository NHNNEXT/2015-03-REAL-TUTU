'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:HeaderCtrl
 * @description
 * # HeaderCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('HeaderCtrl', function ($state, $rootScope, $scope, user, userBroker) {
    $scope.state = $state;
    $scope.user = user;
    userBroker.getSessionUser();

    $scope.logout = userBroker.logout;
  });
