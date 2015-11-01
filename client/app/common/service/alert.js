'use strict';

/**
 * @ngdoc service
 * @name clientApp.alert
 * @description
 * # alert
 * Factory in the clientApp.
 */
angular.module('clientApp')
  .factory('alert', function ($timeout) {

    var timer;
    // Public API here
    return function (message, success, duration) {
      var scope = angular.element(document.querySelector('#alert')).scope();
      if (!duration) {
        duration = 3000;
        $timeout.cancel(timer);
      }
      timer = $timeout(function () {
        scope.showing = false;
        if (!scope.$$phase) {
          scope.$apply();
        }
      }, duration);
      scope.alert = {alert: message, date: new Date(), success: success};
      scope.showing = true;
      if (!scope.$$phase) {
        scope.$apply();
      }
    };
  });
