/**
* @ngdoc service
* @name clientApp.lectureManager
* @description
* # lectureManager
* Service in the clientApp.
*/

(function() {
  'use strict';
  angular.module('clientApp')
  .service('lectureManager', LectureManager);

  /* @ngInject */
  function LectureManager ($http,$q,Lecture) {
    // AngularJS will instantiate a singleton by calling "new" on this function


    this.getLecture = getLecture;
    this.setLecture = setLecture;

    var _store = {};

    var _getLectureInstance = function (id, lectureData) {
      var instance = this._store[id];
      if(instance) {
        instance.setData(lectureData);
      } else {
        instance = new Lecture(lectureData);
        this._store[id] = instance;
      }
      return instance;
    };

    var _search = function (id) {
      return this._store(id);
    };

    var _load = function(id , deferred) {
      var self = this ;
      var lecture = new Lecture();

      lecture
      .load(id)
      .then(function(lectureData) {
        var lecture = self._getLectureInstance(lectureData.id, lectureData);
        deferred.resolve(lecture);

      }, function() {
        deferred.reject();
      });
    };
  }

  function getLecture(id) {
    var deferred = $q.defer();
    var lecture = this._search(id);
    if (lecture) {
      deferred.resolve(lecture);
    } else {
      this._load(id, deferred);
    }
    return deferred.promise;
  }

  function setLecture(lectureData) {
    var self = this;
    var lecture = this_search(lectureData.id);
    if (lecture) {
      lecture.setData(lectureData);
    } else {
      lecture = self._getLectureInstance(lectureData);
    }
    return lecture;
  }

}());
