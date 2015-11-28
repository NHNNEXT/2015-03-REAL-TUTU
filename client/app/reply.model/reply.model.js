angular.module('clientApp')
  .factory('Reply', function (http, User, $q) {
    var Reply = function (obj) {
      if (obj === undefined)
        return;
      if (typeof obj !== "object") {
        return;
      }
      this.setProperties(obj);
    };

    Reply.prototype.setProperties = function (obj) {
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
      var self = this;
      if (this.id === undefined) {
        query.contentId = this.contentId;
        return $q(function (resolve) {
          http.post('/api/v1/reply', query).then(function (result) {
            self.setProperties(result);
            resolve(self);
          });
        });
      }
      return $q(function (resolve) {
        http.put('/api/v1/reply', query).then(function (result) {
          self.setProperties(result);
          resolve(self);
        });
      });
    };

    Reply.prototype.remove = function () {
      return http.delete('/api/v1/reply', {id: this.id});
    };

    Reply.getList = function (query) {
      return http.get('/api/v1/reply', query).then(function (result) {
        if (result === undefined || result.forEach === undefined)
          return;
        var replies = [];
        result.forEach(function (reply) {
          replies.push(new Reply(reply));
        });
        return replies;
      });
    };

    return Reply;
  });
