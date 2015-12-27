angular.module('clientApp')
  .factory('SubmitRequire', function (http, User, $q, Attachment, Content) {
    function SubmitRequire(param) {
      this.done = param.done;
      this.content = new Content(param.content);
      this.id = param.id;
    }

    SubmitRequire.prototype.doToggle = function () {
      http.post('/api/v1/content/submit/require', {id: this.id, done: !this.done}); //NG-Model바뀌는거보다, 이벤트 실행이 먼저되서 !done으로 보내야함.
    };


    return SubmitRequire;
  });
