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
      this.downloadUrl = obj.downloadUrl;
      this.uploadTime = new Date(obj.uploadTime);
      this.ext = obj.originalFileName.split('.').pop();

    };

    return Attachment;
  });
