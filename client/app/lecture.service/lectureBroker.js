(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('lectureBroker', lectureBroker);
  /* @ngInject */
  function lectureBroker(http, user, $q) {
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
      var query = {};
      lecture.contents = undefined;
      lecture.enrolledStudent = undefined;
      lecture.hostUser = undefined;
      lecture.date = undefined;
      lecture.managers = undefined;
      angular.copy(lecture, query);
      query.types = JSON.stringify(lecture.types);
      return http.post('/api/v1/lecture', query, true);
    }

    function findById(lectureId) {
      return $q(function (resolve) {
        http.get('/api/v1/lecture', {id: lectureId}).then(function (lecture) {
          lecture.types = JSON.parse(lecture.types);
          resolve(lecture);
        });
      });
    }

    function getList() {
      return http.get('/api/v1/lecture');
    }

  }
}());
