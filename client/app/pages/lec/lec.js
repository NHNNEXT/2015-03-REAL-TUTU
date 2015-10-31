(function() {
  'use strict';

  angular
  .module('clientApp')
  .config(config);

  function config($stateProvider) {
  $stateProvider
    .state('lec', {
      url: "/lec",
      templateUrl: "/pages/lec/lec.html",
      controller: "LectureCtrl",
      controllerAS: "lecture"
    });
  }
}());
