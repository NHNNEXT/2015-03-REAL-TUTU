angular.module('clientApp')
  .factory('SubmitRequire', function (http, User, $q, Attachment, Content) {

    function SubmitRequire(param) {
      this.done = param.done;
      this.content = new Content(param.content);
      this.submitState = param.submitState;
      this.id = param.id;
    }

    return SubmitRequire;
  });
