angular.module('clientApp').directive('letter',
  /* @ngInject */
  function () {
    return {
      restrict: 'E',
      templateUrl: '/letter.directive.letter/letter.html',
      scope: {
        letter: '='
      }
    };
  });

