angular.module('clientApp')
  .directive('nxSideNav', function () {
    return {
      restrict: 'E',
      scope: {
        contents: '=',
        keyword: '='
      },
      replace: true,
      templateUrl: '/lecture.editor/editor-sidenav.html',
      controller: "menuController"
      //link: function(scope,elem,attrs) {
      //
      //}
    };
  });
// contentlistController 가 Contents 를 들고 있다
