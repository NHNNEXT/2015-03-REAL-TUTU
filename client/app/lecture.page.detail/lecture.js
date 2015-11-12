/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture', {
      url: "/lecture/:id",
      templateUrl: "/lecture.page.detail/lecture.html",
      controller: 'lectureDetailController'
    });
});
