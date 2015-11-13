angular.module('clientApp').factory('Message',
  /* @ngInject */
  function () {
    var types = ['알림','강의 메시지','다가올 일'];

    function Message(type, from, title, body, url) {
      this.type = types[type];
      this.from = from;
      this.title = title;
      this.body = body;
      this.url = url;
    }

    return Message;
  });
