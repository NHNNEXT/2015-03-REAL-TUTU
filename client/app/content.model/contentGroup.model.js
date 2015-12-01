angular.module('clientApp')
  /* @ngInject */
  .factory('ContentGroup', function () {
    function ContentGroup(param) {
      this.id = param.id;
      this.endTime = param.endTime;
      this.startTime = param.startTime;
      this.submitOpen = param.submitOpen;
      this.submit = param.submit;
      this.reply = param.reply;
      this.name = param.name;
    }

    ContentGroup.prototype.hasTime = function(){
      return this.startTime || this.endTime;
    };

    ContentGroup.prototype.hasDuration = function(){
      return this.startTime && this.endTime;
    };

    return ContentGroup;
  });
