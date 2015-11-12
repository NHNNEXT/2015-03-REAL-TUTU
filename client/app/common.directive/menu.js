angular.module('clientApp')
  /* @ngInject */
  .directive('menu', function ($compile, $state) {
    return {
      restrict: 'E',
      scope: {
        state: '@',
        name: '@'
      },
      template: "<li ng-class=\"{active:$state.current.name.match(state)}\"><a ui-sref='{{state}}'>{{name}}</a></li>",
      replace: true,
      link: function postLink(scope) {
        scope.$state = $state;
      }
    };
  });
