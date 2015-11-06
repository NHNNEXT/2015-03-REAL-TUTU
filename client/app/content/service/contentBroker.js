(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('contentBroker', contentBroker);
  /* @ngInject */
  function contentBroker(http, responseCode) {
    this.findById = findById;
    this.getList = getList;
    this.edit = edit;
    this.delete = deleteContent;

    function deleteContent(id) {
      return http.delete('/api/v1/content', {id: id});
    }

    function edit(content) {
      content.writer = undefined;
      return http.post('/api/v1/content', content);
    }

    function findById(id) {
      return http.get('/api/v1/content', {id: id});
    }

    function getList(callback) {
      return http.get('/api/v1/content/list');
    }
  }
}());
