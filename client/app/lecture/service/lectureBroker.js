
/**
 * @ngdoc service
 * @name clientApp.lectureSvc
 * @description
 * # lectureSvc
 * Service in the clientApp.
 */
 (function() {
   'use strict';

angular
  .module('clientApp')
  .service('lectureBroker', lecture);
  /* @ngInject */
  function lecture(http, responseCode) {
    this.findById = findById;
    this.getList = getList;
    this.create = create;

    function create(lecture) {
      http.post('/api/v1/lecture', lecture, function (response) {
        if (response.code === responseCode.SUCCESS) {
          alert('생성되었습니다.');
        }
      });
    }

    function findById(lectureId) {

    }

    function getList(isOnlyMyLecture,params) {

    }

  }
}());
