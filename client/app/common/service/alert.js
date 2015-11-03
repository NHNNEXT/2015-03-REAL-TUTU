'use strict';

/**
 * @ngdoc service
 * @name clientApp.alert
 * @description
 * # alert
 * Factory in the clientApp.
 * @reference https://github.com/Foxandxss/angular-toastr
 */


(function() {
  'use strict';

angular.module('clientApp')
  .service('alert',alert);

  /* @ngInject */
  function alert(toastr) {

    this.success = function(msg) {
      toastr.success(msg,'success!');
    };
    this.info = function(msg) {
      toastr.info(msg,'info');
    };
    this.warning = function(msg) {
      toastr.warning(msg,'warning');
    };
    this.error = function(msg, err) {
      toastr.error(msg,'error!');
      if(err) {
        if(bowser.chrome) {
            console.table({
              'message': msg ,
              'error': err
            });
        } else {
          console.log({'message': msg,'error':err});
        }
      }
    };

    //
    //
    //var timer;
    //// Public API here
    //return function (message, success, duration) {
    //  var scope = angular.element(document.querySelector('#alert')).scope();
    //  if (!duration) {
    //    duration = 3000;
    //    $timeout.cancel(timer);
    //  }
    //  timer = $timeout(function () {
    //    scope.showing = false;
    //    if (!scope.$$phase) {
    //      scope.$apply();
    //    }
    //  }, duration);
    //  scope.alert = {alert: message, date: new Date(), success: success};
    //  scope.showing = true;
    //  if (!scope.$$phase) {
    //    scope.$apply();
    //  }
    //};
  }

}());
