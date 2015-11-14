angular.module('clientApp')
  .factory('Reply', function (http, User) {
    var Reply = function (obj) {
      if (obj === undefined)
        return;
      if (typeof obj !== "object") {
        return;
      }
      this.id = obj.id;
      this.body = obj.body;
      this.writeDate = new Date(obj.writeDate);
      this.writer = new User(obj.writer);
      this.likes = obj.likes;
    };

    Reply.prototype.save = function () {
      var query = {};
      query.id = this.id;
      query.body = this.body;
      if (this.id === undefined)
        return http.post('/api/v1/reply', query, true);
      return http.put('/api/v1/reply', query, true);
    };

    Reply.prototype.remove = function () {
      return http.delete('/api/v1/reply', {id: this.id}, true);
    };

    return Reply;
  });
