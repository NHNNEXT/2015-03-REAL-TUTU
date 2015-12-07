angular.module('clientApp')
  .factory('Submit', function (http, User, $q) {
    var Submit = function (obj) {
      if (obj === undefined)
        return;
      if (typeof obj !== "object") {
        return;
      }
      this.setProperties(obj);
    };

    Submit.prototype.setProperties = function (obj) {
      this.id = obj.id;
      this.body = obj.body;
      this.attachments = obj.attachments;
      this.writeDate = new Date(obj.writeDate);
      this.writer = new User(obj.writer);
    };

    Submit.prototype.save = function () {
      var query = {};
      query.id = this.id;
      query.body = this.body;
      query.submitId = this.submitId;
      if (this.attachments) {
        query.attachments = [];
        this.attachments.forEach(function (attachment) {
          query.attachments.push(attachment.id);
        });
      }
      var self = this;
      if (this.id === undefined) {
        return $q(function (resolve) {
          http.post('/api/v1/submit', query, true).then(function (result) {
            self.setProperties(result);
            resolve(self);
          });
        });
      }
      return $q(function (resolve) {
        http.put('/api/v1/submit', query, true).then(function (result) {
          self.setProperties(result);
          resolve(self);
        });
      });
    };

    Submit.prototype.remove = function () {
      return http.delete('/api/v1/submit', {id: this.id});
    };

    Submit.getList = function (query) {
      return http.get('/api/v1/submit', query).then(function (result) {
        if (result === undefined || result.forEach === undefined)
          return;
        var replies = [];
        result.forEach(function (reply) {
          replies.push(new Submit(reply));
        });
        return replies;
      });
    };

    return Submit;
  });
