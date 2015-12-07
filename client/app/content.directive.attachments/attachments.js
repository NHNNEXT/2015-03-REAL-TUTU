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
        this.tmps = [];

        $scope.$watch(function () {
          return self.files;
        }, function (files) {
          if (!files)
            return;
          files.forEach(function (file) {
            self.tmps.push(file);
            file.progress = 0;
            Upload.upload({
              url: '/api/v1/upload/attachment',
              data: {file: file}
            }).then(function (resp) {
              if (!self.content.attachments)
                self.content.attachments = [];
              self.content.attachments.push(new Attachment(resp.data.result));
              self.tmps.remove(file);
            }, function () {
              alert.error("업로드 실패 했습니다.");
            }, function (evt) {
              file.progress = parseInt(100.0 * evt.loaded / evt.total);
            });
          });
        });
      }
    };
  });

