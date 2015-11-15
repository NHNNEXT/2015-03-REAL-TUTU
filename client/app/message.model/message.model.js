angular.module('clientApp').factory('Message',
  /* @ngInject */
  function (http) {
    //var types = ['알림','강의 메시지','다가올 일'];

    function Message() {
    }

    Message.getList = function (page) {
      return http.get('/api/v1/message', {page: page});
    };
    return Message;
  });
