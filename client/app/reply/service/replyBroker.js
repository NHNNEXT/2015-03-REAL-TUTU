'use strict';
angular
  .module('clientApp')
  .service('replyBroker', replyBroker);
/* @ngInject */
function replyBroker(http) {
  this.save = save;
  this.remove = remove;

  function save(reply) {
    return http.post('/api/v1/reply', reply);
  }

  function remove(id) {
    return http.delete('/api/v1/reply', {id: id});
  }
}
