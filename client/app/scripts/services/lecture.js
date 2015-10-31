/**
 * @ngdoc service
 * @name clientApp.lecture
 * @description
 * # lecture
 * Factory in the clientApp.
 * @author hwangjjung
 */

 // info: 전역에 함수가 생성되지 않도록 IIFE 패턴으로 만듬.
(function() {
  'use strict';

  angular.module('clientApp')
    .factory('lecture', LectureFactory);

    /* @ngInject */
     function  LectureFactory($http,$q) {

      function Lecture(lectureData) {
        if(lectureData) {
          this.setData(lectureData);
        }
      }


    Lecture.prototype = {

      api: 'api/v1/lectures',

      setData: function (lectureData) {
        angular.extend(this,lectureData);
      },
      load: function(id) {
        var self = this;
        var deferred= $q.defer();
        $http
          .get(self.api+id)
          .success(function(lectureData) {
            self.setData(lectureData);
            deferred.resolve(lectureData);
          })
          .error(function () {
            deferred.reject();
          });
        return deferred.promise;
      },

      delete: function(id) {
          //
      },
      update: function(id) {
        //
      }
    };
    return Lecture;
  }
}());
