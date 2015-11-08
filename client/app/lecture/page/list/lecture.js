(function() {
  'use strict';

  angular
  .module('clientApp')
  .config(config);

  function config($stateProvider) {
  $stateProvider
    .state('lectures', {
      url: "/lectures",
      templateUrl: "/lecture/page/list/lectureList.html",
      controller: "LectureController",
      controllerAS: "lecture"
    });
  }
}());
