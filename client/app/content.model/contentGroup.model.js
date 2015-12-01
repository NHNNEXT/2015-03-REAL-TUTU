angular.module('clientApp')
  /* @ngInject */
  .factory('ContentGroup', function () {

    var contentTypes = {
      GENERAL: {},
      NOTICE: {},
      SUBMIT: {endTime: true, submit: true},
      SCHEDULE: {startTime: true, endTime: true}
    };

    function ContentGroup(param) {
      this.id = param.id;
      this.contentType = contentTypes[param.contentType];
      this.submitOpen = param.submitOpen;
      this.reply = param.reply;
      this.name = param.name;
    }

    ContentGroup.prototype.hasTime = function () {
      return this.startTime || this.endTime;
    };

    ContentGroup.prototype.hasDuration = function () {
      return this.startTime && this.endTime;
    };

    return ContentGroup;
  });
