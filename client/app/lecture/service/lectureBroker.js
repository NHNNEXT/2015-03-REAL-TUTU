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
  function lecture(http, responseCode, $q) {
    this.findById = findById;
    this.getList = getList;
    this.create = create;
    this.enroll = enroll;

    function enroll(id) {
      return $q(function (resolve, reject) {
        http.post('/api/v1/lecture/enroll', {id: id}, function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }

    function create(lecture) {
      return $q(function (resolve, reject) {
        http.post('/api/v1/lecture', lecture, function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }

    function findById(lectureId) {
      return $q(function (resolve, reject) {
        http.get('/api/v1/lecture', {id: lectureId}, function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }

    function getList() {
      return $q(function (resolve, reject) {
        http.get('/api/v1/lecture', function (response) {
          if (response.code === responseCode.SUCCESS) {
            resolve(response.result);
          }
          reject(response);
        });
      });
    }

  }
}());
