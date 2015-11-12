(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('contentBroker', contentBroker);
  /* @ngInject */
  function contentBroker(http, $q) {
    this.findById = findById;
    this.getList = getList;
    this.edit = edit;
    this.remove = remove;

    function remove(id) {
      return http.delete('/api/v1/content', {id: id}, true);
    }

    function edit(content) {
      content.writer = undefined;
      content.replies = undefined;
      return http.post('/api/v1/content', content, true);
    }

    function findById(id) {
      return $q(function(resolve){
        http.get('/api/v1/content', {id: id}).then(function(content){
          content.types = JSON.parse(content.types);
          resolve(content);
        });
      });
    }

    function getList() {
      return http.get('/api/v1/content/list');
    }
  }
}());
