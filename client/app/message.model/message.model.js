angular.module('clientApp').factory('Message',
  /* @ngInject */
  function (http) {
    //var types = ['알림','강의 메시지','다가올 일'];

    function Message() {
    }

    Message.getList = function(){
      return http.get('/api/v1/message');
    };



    return Message;
  });
