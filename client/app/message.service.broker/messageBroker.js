
(function(){
  'use strict';
  angular
    .module('clientApp')
    .service('messageBroker', messageBroker);
  /* @ngInject */
  function messageBroker(http) {
    this.save = save;
    this.remove = remove;

    function save(reply) {
      return http.post('/api/v1/reply', reply, true);
    }

    function remove(id) {
      return http.delete('/api/v1/reply', {id: id}, true);
    }
  }
}());
