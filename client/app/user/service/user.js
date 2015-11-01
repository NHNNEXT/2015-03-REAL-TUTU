'use strict';

/**
 * @ngdoc service
 * @name clientApp.user
 * @description
 * # user
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('user', function () {
    this.name = "황정민";
    this.profile = "/favicon.ico";
    this.logged = true;
    this.id = 1;
    var lectures = this.lectures = [];
    lectures.push(new Lecture(2014, 1, "자료구조와 알고리즘2", 0, 1, 3, [new ClassTime(3, "09:00", "11:00")], "김동진", 1, "설명"));
    lectures.push(new Lecture(2013, 2, "료구조와 알고리즘2", 2, 2, 4, [new ClassTime(3, "09:00", "11:00")], "김동진", 2, "설명"));
    lectures.push(new Lecture(2012, 3, "구조와 알고리즘2", 4, 1, 5, [new ClassTime(3, "09:00", "11:00")], "김동진", 3, "설명"));
    lectures.push(new Lecture(2011, 4, "조와 알고리즘2", 5, 1, 6, [new ClassTime(3, "09:00", "11:00")], "김동진", 4, "설명"));
    lectures.push(new Lecture(2010, 5, "와 알고리즘2", 6, 1, 7, [new ClassTime(3, "09:00", "11:00")], "김동진", 5, "설명"));


  });
