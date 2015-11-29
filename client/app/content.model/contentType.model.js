angular.module('clientApp')
  /* @ngInject */
  .factory('ContentType', function () {
    function ContentType(param) {
      this.endTime = param.endTime;
      this.startTime = param.startTime;
      this.submitOpen = param.submitOpen;
      this.submit = param.submit;
      this.reply = param.reply;
      this.name = param.name;
    }

    ContentType.prototype.hasTime = function(){
      return this.startTime || this.endTime;
    };

    ContentType.prototype.hasDuration = function(){
      return this.startTime && this.endTime;
    };

    return ContentType;
  });
