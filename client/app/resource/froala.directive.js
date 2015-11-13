angular.module('clientApp')
  /* @ngInject */
  .directive('froala', function () {
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
          toolbarInline: true,
          zIndex: 100,
          spellcheck: false,
          toolbarButtons: ['fontFamily', 'fontSize', 'bold', 'underline', 'strikeThrough', 'emoticons', '-',
            'color', 'align', 'insertLink', 'insertImage', 'insertTable', 'html']
        });

        function updateView() {
          ngModel.$setViewValue(froalaEditor.froalaEditor('html.get'));
          if (!scope.$root.$$phase) {
            scope.$apply();
          }
        }

        ngModel.$render = function () {
          froalaEditor.froalaEditor('html.set', ngModel.$viewValue || '', true);
        };

        froalaEditor.on('froalaEditor.contentChanged', function () {
          updateView();
        });
      }
    };
  });
