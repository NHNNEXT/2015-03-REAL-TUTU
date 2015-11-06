(function() {
  'use strict';

  angular
  .module('clientApp')
  .config(config);

  function config($stateProvider) {
  $stateProvider
    .state('lectures', {
      url: "/lectures",
      headerClass:'navbar-fixed-top navbar-transparent',
      templateUrl: "/lecture/page/list/lectureList.html",
      controller: "LectureController",
      controllerAS: "lecture"
    });
  }
}());
