angular.module('clientApp').directive('message',
  /* @ngInject */
  function () {
    return {
      restrict: 'E',
      templateUrl: '/message.directive/message.html',
      scope: {
        message: '='
      }
    };
  });
