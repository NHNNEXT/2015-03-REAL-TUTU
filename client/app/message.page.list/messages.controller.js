angular.module('clientApp')
  .controller('messagesController',
    /* @ngInject */
    function (Message, Page, $rootScope) {

      var page = this.page = new Page();

      var self = this;

      function messageInit() {
        self.messages = Message.messages;
        page.next();
        page.return(Message.messages.length);
      }

      if (!self.messages) {
        messageInit();
      }

      $rootScope.$on('messageReceived', function () {
        if (!self.messages || self.messages.length === 0) {
          messageInit();
          return;
        }
        Message.messages.forEach(function (message) {
          if (!self.messages.findById(message.id))
            self.messages.unshift(message);
        });
      });

      self.more = function () {
        Message.getList(page.next()).then(function (messages) {
          page.return(messages.length);
          messages.forEach(function (message) {
            if (self.messages === undefined)
              self.messages = [];
            self.messages.push(message);
          });
        });
      };

    });

