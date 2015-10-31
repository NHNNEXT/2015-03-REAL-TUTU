
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
  .service('lecture', lecture);
  /* @ngInject */
  function lecture(Lectures) {
    this.getLecture = getLecture;
    this.getLectures = getLectures;
    this.create = create;


    function getLecture(lectureId) {
      return Lectures.one(lectureId).get();
    }

    function getLectures(isOnlyMyLecture,params) {
      var lecture;
      if(isOnlyMyLecture) {
        lecture = {type: 'RELATED' , sort: '-CREATED'};
      } else {
        lecture = {type: 'UNRELATED', sort: '-CREATED'};
      }

      if(params) {
        params = angular.extend(lecture , params);
      } else {
        params = lecture;
      }
      return Lectures.customGET('', params);
    }

    function create(params) {
      return Lectures.customPOST(params);
    }
  }
}());
