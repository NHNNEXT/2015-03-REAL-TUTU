angular.module('clientApp').factory('Message',
  /* @ngInject */
  function (http, $q, $timeout, rootUser, $rootScope) {
    function Message(obj) {
      if (typeof obj !== "object")
        return;
      this.id = obj.id;
      this.message = obj.message;
      this.checked = obj.checked;
      this.type = obj.type;
      this.date = new Date(obj.date);
      this.url = obj.url;
    }

    Message.prototype.reading = function () {
      var self = this;
      http.put('/api/v1/message', {id: this.id}).then(function () {
        self.checked = true;
        newCheck();
      });
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

    Message.messages = [];
    Message.getMessages = function () {
      if (!rootUser.isLogged())
        return;
      Message.getList(0).then(function (messages) {
        $timeout(Message.getMessages, 30000);
        if (angular.equals(Message.messages, messages))
          return;
        Message.messages = messages;
        newCheck();
        $rootScope.$broadcast('messageReceived');
      });
    };

    $rootScope.$on('userStateChange', function () {
      Message.getMessages();
    });

    $timeout(Message.getMessages, 300);

    function newCheck() {
      Message.new = 0;
      Message.messages.forEach(function (message) {
        if (message.checked)
          return;
        Message.new++;
      });
    }

    return Message;
  });
