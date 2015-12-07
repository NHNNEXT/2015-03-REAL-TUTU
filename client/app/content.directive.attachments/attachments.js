angular.module('clientApp').directive('attachments',
  /* @ngInject */
  function () {
    return {
      restrict: 'E',
      templateUrl: '/content.directive.attachments/attachments.html',
      scope: {
        content: '=',
        readonly:'='
      }, controller: function (Upload, $scope, alert, Attachment, $window, confirm) {
        $scope.progress = 0;

        $scope.download = function (attachment) {
          if (!confirm("파일을 다운로드 합니다."))
            return;
          $window.open(attachment.downloadUrl, '_blank');
        };

        $scope.delete = function (attachment, attachments) {
          if (!confirm("삭제하시겠습니까?"))
            return;
          attachments.remove(attachment);
        };

        $scope.$watch('file', function (file) {
          if (!file)
            return;
          $scope.uploading = true;
          Upload.upload({
            url: '/api/v1/upload/attachment',
            data: {file: file}
          }).then(function (resp) {
            $scope.uploading = false;
            if (!$scope.content.attachments)
              $scope.content.attachments = [];
            $scope.content.attachments.push(new Attachment(resp.data.result));
          }, function () {
            alert.error("업로드 실패 했습니다.");
            $scope.uploading = false;
          }, function (evt) {
            $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
          });
        });
      }
    };
  });

