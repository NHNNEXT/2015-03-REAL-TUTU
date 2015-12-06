angular.module('clientApp')
  /* @ngInject */
  .factory('ContentGroup', function () {

    function ContentGroup(param) {
      this.id = param.id;
      this.contentType = param.contentType;
      this.submitOpen = param.submitOpen;
      this.reply = param.reply;
      this.name = param.name;
    }

    return ContentGroup;
  });
