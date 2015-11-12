(function () {
  'use strict';

  angular.module('clientApp')
    .service('alert', alert);

  /* @ngInject */
  function alert(toastr) {

    this.success = function (msg) {
      toastr.success(msg, 'success!');
    };
    this.info = function (msg) {
      toastr.info(msg, 'info');
    };
    this.warning = function (msg) {
      toastr.warning(msg, 'warning');
    };
    this.error = function (msg, err) {
      toastr.error(msg, 'error!');
      if (err) {
        if (bowser.chrome) {
          console.table({
            'message': msg,
            'error': err
          });
        } else {
          console.log({'message': msg, 'error': err});
        }
      }
    };

  }
}());
