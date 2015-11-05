'use strict';
angular
  .module('clientApp')
  .service('replyBroker', replyBroker);
/* @ngInject */
function replyBroker(http, responseCode, $q) {
  this.write = write;

  function write(reply) {
    return $q(function (resolve, reject) {
      http.post('/api/v1/reply', reply, function (response) {
        if (response.code === responseCode.SUCCESS) {
          resolve(response.result);
        }
        reject(response);
      });
    });
  }
}
