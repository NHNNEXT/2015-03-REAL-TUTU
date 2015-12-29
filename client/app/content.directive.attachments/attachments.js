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
        readonly: '='
      }, controller: function (Upload, $scope, alert, Attachment, $window, confirm) {
        this.progress = 0;

        this.getIcon = function (ext) {
          if (!ext)
            return;
          var iconPath = "/resource/icon/";
          switch (ext) {
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
      }
    };
  });

