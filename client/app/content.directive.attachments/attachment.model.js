angular.module('clientApp')
  /* @ngInject */
  .factory('Attachment', function () {
    function Attachment(param) {
      if (param === undefined || param === null)
        return;
      if (typeof param === "object") {
        this.setProperties(param);
      }
    }

    Attachment.prototype.setProperties = function (obj) {
      this.id = obj.id;
      this.originalFileName = obj.originalFileName;
      this.ext = this.originalFileName.split('.').pop();
      this.icon = getIcon(this);
      this.downloadUrl = obj.downloadUrl;
      this.uploadTime = new Date(obj.uploadTime);
    };

    function getIcon(attachment) {
      if (!attachment)
        return;
      var iconPath = "/resource/icon/";
      switch (attachment.ext) {
        case "mp3":
          return iconPath + "music.svg";
        case "wma":
          return iconPath + "music.svg";
        case "pdf":
          attachment.viewInsertAble = true;
          return iconPath + "pdf.png";
        case "doc":
          attachment.viewInsertAble = true;
          return iconPath + "word.svg";
        case "docx":
          attachment.viewInsertAble = true;
          return iconPath + "pdf.svg";
        case "xls":
          attachment.viewInsertAble = true;
          return iconPath + "excel.svg";
        case "xlsx":
          attachment.viewInsertAble = true;
          return iconPath + "excel.svg";
        case "ppt":
          attachment.viewInsertAble = true;
          return iconPath + "ppt.svg";
        case "pptx":
          attachment.viewInsertAble = true;
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
    }

    return Attachment;
  });
