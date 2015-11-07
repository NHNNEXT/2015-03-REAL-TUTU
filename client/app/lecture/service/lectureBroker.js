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
    .service('lectureBroker', lectureBroker);
  /* @ngInject */
  function lectureBroker(http, user) {
    this.findById = findById;
    this.getList = getList;
    this.edit = edit;
    this.enroll = enroll;
    this.remove = remove;

    function remove(id) {
      return http.delete('/api/v1/lecture', {id: id}, true);
    }

    function enroll(id) {
      if (!user.loginCheck())
        return;
      return http.post('/api/v1/lecture/enroll', {id: id}, true);
    }

    function edit(lecture) {
      lecture.contents = undefined;
      lecture.enrolledStudent = undefined;
      lecture.hostUser = undefined;
      lecture.date = undefined;
      lecture.managers = undefined;
      return http.post('/api/v1/lecture', lecture, true);
    }

    function findById(lectureId) {
      return http.get('/api/v1/lecture', {id: lectureId});
    }

    function getList() {
      return http.get('/api/v1/lecture');
    }

  }
}());
