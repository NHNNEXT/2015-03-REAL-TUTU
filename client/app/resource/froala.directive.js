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
          spellcheck: false,
          imageUploadURL: '/api/v1/upload',
          toolbarButtons: ['fontFamily', 'fontSize', 'color', 'align', '-',
            'bold', 'italic', 'underline', 'strikeThrough'],
          toolbarButtonsMD: ['fontFamily', 'fontSize', 'color', 'align', '-',
            'bold', 'italic', 'underline', 'strikeThrough'],
          videoEditButtons: ['videoDisplay', 'videoAlign', 'videoSize', 'videoRemove', 'fitSize']
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
