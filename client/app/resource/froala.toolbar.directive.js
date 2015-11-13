angular.module('clientApp')
  /* @ngInject */
  .directive('froalaToolbar', function () {
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function postLink(scope, element, attrs, ngModel) {

        var froalaEditor = $(element).froalaEditor({
          language: 'ko',
          fontFamily: {
            "'Noto Sans', sans-serif": '본 고딕',
            "'Nanum Myeongjo',serif": '나눔 명조'
          },
          zIndex: 100,
          spellcheck: false,
          toolbarButtons: [      'insertHR', 'insertLink', 'insertImage', 'insertVideo', 'insertFile', 'insertTable', 'selectAll', 'html']
        });

        function updateView() {
          ngModel.$setViewValue(froalaEditor.froalaEditor('html.get'));
          if (!scope.$root.$$phase) {
            scope.$apply();
          }
        }

        froalaEditor.on('froalaEditor.contentChanged', function () {
          updateView();
        });
      }
    };
  });
