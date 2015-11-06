'use strict';
angular
  .module('clientApp')
  .service('replyBroker', replyBroker);
/* @ngInject */
function replyBroker(http, responseCode, $q) {
  this.write = write;

  function write(reply) {
    return http.post('/api/v1/reply', reply);
  }
}
