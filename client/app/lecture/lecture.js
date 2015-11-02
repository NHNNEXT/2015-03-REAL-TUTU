(function() {
  'use strict';

  angular
  .module('clientApp')
  .config(config);

  function config($stateProvider) {
  $stateProvider
    .state('lec', {
      url: "/lecture",
      templateUrl: "/lecture/list/lectureList.html",
      controller: "LectureController",
      controllerAS: "lecture"
    });
  }
}());
