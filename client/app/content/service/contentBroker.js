(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('contentBroker', contentBroker);
  /* @ngInject */
  function contentBroker($log, http, responseCode, alert) {
    this.findById = findById;
    this.getList = getList;
    this.create = create;

    function create(content, callback) {
      http.post('/api/v1/content', content, function (response) {
        if (response.code === responseCode.SUCCESS) {
          callback(response.result);
        }
      });
    }

    function findById(id, callback) {
      http.get('/api/v1/content', {id: id}, function (response) {
        if (response.code === responseCode.SUCCESS) {
          callback(response.result);
        }
      });
    }

    function getList(callback) {
      //[TODO] 리퀘스트 두번보냄 원인 확인
      $log.debug("get Lecture List");
      http.get('/api/v1/lecture', function (response) {
        if (response.code === responseCode.SUCCESS) {
          callback(response.result);
        }
      });
    }

  }
}());
