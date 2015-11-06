'use strict';
angular
  .module('clientApp')
  .service('replyBroker', replyBroker);
/* @ngInject */
function replyBroker(http) {
  this.save = save;
  this.deleteReply = deleteReply;

  function save(reply) {
    return http.post('/api/v1/reply', reply);
  }

  function deleteReply(id) {
    return http.delete('/api/v1/reply', {id: id});
  }
}
