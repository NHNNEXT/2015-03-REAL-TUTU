angular.module('clientApp')
  .factory('Content', function (http, $sce, $q) {
    function Content(id) {
      if (id === undefined)
        return;
      var self = this;
      http.get('/api/v1/content', {id: id}).then(function (result) {
        self.setProperties(result);
      });
    }

    Content.prototype.setProperties = function (obj) {
      this.replies = obj.replies;
      this.repliesSize = obj.repliesSize;
      this.writer = obj.writer;
      this.lectureName = obj.lectureName;
      this.lectureId = obj.lectureId;
      this.id = obj.id;
      this.title = obj.title;
      this.body = obj.body;
      this.writeDate = new Date(obj.writeDate);
      this.dueDate = new Date(obj.dueDate);
      this.type = obj.type;
      this.hits = obj.hits;
      this.likes = obj.likes;
      try {
        this.types = JSON.parse(obj.types);
      } catch (e) {
      }
    };

    Content.getList = function () {
      return $q(function (resolve) {
        http.get('/api/v1/content/list').then(function (response) {
          var result = [];
          if (response.forEach === undefined)
            return;
          response.forEach(function (each) {
            var content = new Content();
            content.setProperties(each);
            result.push(content);
          });
          resolve(result);
        });
      });
    };

    Content.prototype.getBodyAsHtml = function () {
      return $sce.trustAsHtml(this.body);
    };

    Content.prototype.update = function () {
      var query = {};
      return http.post('/api/v1/content', query, true);
    };

    Content.prototype.remove = function () {
      if (!confirm("삭제하시겠습니까?"))
        return;
      return http.delete('/api/v1/content', {id: this.id}, true);
    };

    return Content;
  });
