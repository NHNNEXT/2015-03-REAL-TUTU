angular.module('clientApp').directive('attachments',
  /* @ngInject */
  function () {
    return {
      restrict: 'E',
      templateUrl: '/content.directive.attachments/attachments.html',
      bindToController: true,
      controllerAs: 'ctrl',
      scope: {
        attachments: '=',
        readonly: '=',
        viewer: '='
      }, controller: function (Upload, $scope, alert, Attachment, $window, confirm) {
        this.progress = 0;

        this.download = function (attachment) {
          confirm("파일을 다운로드 합니다.", "받을 파일 : " + attachment.originalFileName, function () {
            $window.open(attachment.downloadUrl, '_blank');
          });
        };

        this.delete = function (attachment, attachments) {
          confirm("삭제하시겠습니까?", "첨부한 파일이 삭제됩니다.", function () {
            attachments.remove(attachment);
          });
        };

        this.insertViewer = function (attachment) {
          confirm("미리보기를 삽입합니다.", " 삽입할 내용 : " + attachment.originalFileName, function () {
            $('[froala]').froalaEditor('video.insert',
              '<iframe src="' + 'http://docs.google.com/gview?url=http://begin.at' + attachment.downloadUrl + '&embedded=true" style="width:600px; height:500px;" frameborder="0"></iframe>');
          });
        };
      }
    };
  });

