angular.module('clientApp').factory('Message',
  /* @ngInject */
  function (http, $q) {
    function Message(obj) {
      if (typeof obj !== "object")
        return;
      this.id = obj.id;
      this.message = obj.message;
      this.read = obj.read;
      this.type = obj.type;
      this.date = new Date(obj.date);
      this.url = obj.url;
    }

    Message.prototype.reading = function () {
      http.put('/api/v1/message', {id: this.id});
    };

    Message.getList = function (page) {
      return $q(function (resolve) {
        http.get('/api/v1/message', {page: page}).then(function (result) {
          var messages = [];
          result.forEach(function (message) {
            messages.push(new Message(message));
          });
          resolve(messages);
        });
      });
    };
    return Message;
  });
