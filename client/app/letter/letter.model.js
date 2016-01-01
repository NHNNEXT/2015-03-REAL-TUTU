angular.module('clientApp').factory('Letter',
  /* @ngInject */
  function (http, $q, User, Message) {
    function Letter(obj) {
      if (typeof obj !== "object")
        return;
      this.id = obj.id;
      this.message = obj.message;
      this.checked = obj.checked;
      this.receiver = new User(obj.receiver);
      this.sender = new User(obj.sender);
      this.date = new Date(obj.date);
    }

    Letter.send = function (message, receiver) {
      return http.post('/api/v1/letter', {message: message, receiverId: receiver.id});
    };

    Letter.getList = function (page) {
      return $q(function (resolve) {
        http.get('/api/v1/letter', {page: page}).then(function (result) {
          var letters = [];
          Message.removeLetterMessage();
          result.forEach(function (message) {
            letters.push(new Letter(message));
          });
          resolve(letters);
        });
      });
    };


    return Letter;
  });
