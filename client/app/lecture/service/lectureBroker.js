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
  function lecture(http, responseCode) {
    this.findById = findById;
    this.getList = getList;
    this.create = create;
    this.enroll = enroll;

    function enroll(id) {
      return http.post('/api/v1/lecture/enroll', {id: id});
    }

    function create(lecture) {
      return http.post('/api/v1/lecture', lecture);
    }

    function findById(lectureId) {
      return http.get('/api/v1/lecture', {id: lectureId});
    }

    function getList() {
      return http.get('/api/v1/lecture');
    }

  }
}());
