angular.module('clientApp')
  .directive('nxNavTab', function ($state) {
    return {
      restrict:'E',
      replace: true,
      templateUrl: '/common.directive.nav.tab/nx-nav-tab.html',
      link: function(scope) {
        console.log($state,scope);
        //scope.vm = {};
        //var vm = scope.vm;
      }
    };
  });
