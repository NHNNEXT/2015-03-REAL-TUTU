(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('contentBroker', contentBroker);
  /* @ngInject */
  function contentBroker($log, http, responseCode, $q) {
    this.findById = findById;
    this.getList = getList;
    this.create = create;

    function create(content) {
      return $q(function (resolve, reject) {
        http.post('/api/v1/content', content, function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
        });
        reject(response);
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
