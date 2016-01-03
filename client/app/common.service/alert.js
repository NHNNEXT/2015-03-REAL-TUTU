(function () {
  'use strict';

  angular.module('clientApp')
    .service('alert', alert);

  /* @ngInject */
  function alert(toastr, $timeout) {
    var alertQue = {success: [], alert: [], info: [], error: []};

    function inQue(type, msg) {
      if (alertQue[type].includes(msg)) {
        return true;
      }
      alertQue[type].push(msg);
      $timeout(function () {
        alertQue[type].remove(msg);
      }, 500);
      return false;
    }

    this.success = function (msg) {
      if (inQue('success', msg))
        throw "같은 메세지입니다.";
      toastr.success(msg, '성공');
    };
    this.info = function (msg) {
      if (inQue('info', msg))
        throw "같은 메세지입니다.";
      toastr.info(msg, '알림');
    };
    this.warning = function (msg) {
      if (inQue('warning', msg))
        throw "같은 메세지입니다.";
      toastr.warning(msg, '경고');
    };
    this.error = function (msg, err) {
      if (inQue('error', msg))
        throw "같은 메세지입니다.";
      toastr.error(msg, '오류');
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
