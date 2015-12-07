angular.module('clientApp').directive('attachments',
  /* @ngInject */
  function () {
    return {
      restrict: 'E',
      templateUrl: '/content.directive.attachments/attachments.html',
      bindToController: true,
      controllerAs: 'ctrl',
      scope: {
        content: '=',
        readonly: '='
      }, controller: function (Upload, $scope, alert, Attachment, $window, confirm) {
        this.progress = 0;

        this.download = function (attachment) {
          if (!confirm("파일을 다운로드 합니다."))
            return;
          $window.open(attachment.downloadUrl, '_blank');
        };

        this.delete = function (attachment, attachments) {
          if (!confirm("삭제하시겠습니까?"))
            return;
          attachments.remove(attachment);
        };

        var self = this;

        $scope.$watch(function () {
          return self.file;
        }, function (file) {
          if (!file)
            return;
          self.uploading = true;
          Upload.upload({
            url: '/api/v1/upload/attachment',
            data: {file: file}
          }).then(function (resp) {
            self.uploading = false;
            if (!self.content.attachments)
              self.content.attachments = [];
            self.content.attachments.push(new Attachment(resp.data.result));
          }, function () {
            alert.error("업로드 실패 했습니다.");
            self.uploading = false;
          }, function (evt) {
            self.progress = parseInt(100.0 * evt.loaded / evt.total);
          });
        });
      }
    };
  });

