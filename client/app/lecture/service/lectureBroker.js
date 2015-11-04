/**
 * @ngdoc service
 * @name clientApp.lectureSvc
 * @description
 * # lectureSvc
 * Service in the clientApp.
 */
(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('lectureBroker', lecture);
  /* @ngInject */
  function lecture($log, http, responseCode, alert) {
    this.findById = findById;
    this.getList = getList;
    this.create = create;
    this.enroll = enroll;

    function enroll(id) {
      http.post('/api/v1/lecture/enroll', {id: id}, function (response) {
        if (response.code === responseCode.SUCCESS) {
          alert('생성되었습니다.');
        }
      });
    }

    function create(lecture) {
      http.post('/api/v1/lecture', lecture, function (response) {
        if (response.code === responseCode.SUCCESS) {
          alert('생성되었습니다.');
        }
      });
    }

    function findById(lectureId, callback) {
      http.get('/api/v1/lecture', {id: lectureId}, function (response) {
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
