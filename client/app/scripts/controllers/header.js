'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:HeaderCtrl
 * @description
 * # HeaderCtrl
 * Controller of the clientApp
 */
angular.module('clientApp')
  .controller('HeaderCtrl', function ($state, $rootScope, $scope, user) {
    $rootScope.$on('$stateChangeSuccess',
      function (event, toState) {
      //function (event, toState, toParams, fromState, fromParams) {
        $state.current = toState;
      }
    );
    $scope.state = $state;
    $scope.user = user;
  });
