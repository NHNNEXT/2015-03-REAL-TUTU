(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('contentBroker', contentBroker);
  /* @ngInject */
  function contentBroker(http, responseCode, $q) {
    this.findById = findById;
    this.getList = getList;
    this.edit = edit;
    this.delete = deleteContent;

    function deleteContent(id) {
      return $q(function (resolve, reject) {
        http.delete('/api/v1/content', {id: id}, function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }

    function edit(content) {
      return $q(function (resolve, reject) {
        content.writer = undefined;
        http.post('/api/v1/content', content, function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }

    function findById(id) {
      return $q(function (resolve, reject) {
        http.get('/api/v1/content', {id: id}, function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }

    function getList(callback) {
      return $q(function (resolve, reject) {
        http.get('/api/v1/content/list', function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }
  }
}());
