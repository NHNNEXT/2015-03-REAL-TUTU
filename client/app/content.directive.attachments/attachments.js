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
        editorId: '@'
      }, controller: function (Upload, $scope, alert, Attachment, $window, confirm) {
        this.progress = 0;
        this.getIcon = function (extension) {
          if (!extension)
            return;
          var iconPath = "/resource/icon/";
          switch (extension) {
            case "mp3":
              return iconPath + "music.svg";
            case "wma":
              return iconPath + "music.svg";
            case "pdf":
              return iconPath + "pdf.png";
            case "doc":
              return iconPath + "word.svg";
            case "docx":
              return iconPath + "pdf.svg";
            case "xls":
              return iconPath + "excel.svg";
            case "xlsx":
              return iconPath + "excel.svg";
            case "ppt":
              return iconPath + "ppt.svg";
            case "pptx":
              return iconPath + "ppt.svg";
            case "hwp":
              return iconPath + "hwp.png";
            case "jpg":
              return iconPath + "image.svg";
            case "gif":
              return iconPath + "image.svg";
            case "png":
              return iconPath + "image.svg";
            case "zip":
              return iconPath + "zip.png";
          }
          return iconPath + "file.svg";
        };

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

        var self = this;
        this.tmps = [];

        var serverUrl = "http://begin.at";

        this.previewAble = function (extention) {
          var able = ["pdf", "pptx", "ppt", "xls", "xlsx", "doc", "docx"];
          return able.includes(extention);
        };

        this.makePreview = function (attachment) {
          if (!self.editorId)
            return;
          confirm("미리보기를 삽입합니다.", "파일명 : " + attachment.originalFileName, function () {
            $('#' + self.editorId).froalaEditor('video.insert',
              "<iframe src='http://docs.google.com/gview?url=" +
              serverUrl + attachment.originalFileName +
              "&embedded=true' frameborder='0'></iframe>"
            );
          });
        };

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
              if (!self.attachments)
                self.attachments = [];
              var attachment = new Attachment(resp.data.result);
              self.attachments.push(attachment);
              self.tmps.remove(file);
            }, function () {
              alert.error("업로드 실패 했습니다.");
              self.tmps.remove(file);
            }, function (evt) {
              file.progress = parseInt(100.0 * evt.loaded / evt.total);
            });
          });
        });
      }
    };
  });

