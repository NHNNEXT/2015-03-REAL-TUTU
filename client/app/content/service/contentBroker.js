(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('contentBroker', contentBroker);
  /* @ngInject */
  function contentBroker(http) {
    this.findById = findById;
    this.getList = getList;
    this.edit = edit;
    this.remove = remove;

    function remove(id) {
      return http.delete('/api/v1/content', {id: id}, true);
    }

    function edit(content) {
      content.writer = undefined;
      return http.post('/api/v1/content', content, true);
    }

    function findById(id) {
      return http.get('/api/v1/content', {id: id});
    }

    function getList() {
      return http.get('/api/v1/content/list');
    }
  }
}());
