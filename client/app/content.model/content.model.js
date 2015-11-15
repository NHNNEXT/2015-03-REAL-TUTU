angular.module('clientApp')
  /* @ngInject */
  .factory('Content', function (http, $sce, $q, $state, confirm, Reply) {
    function Content(param) {
      if (param === undefined)
        return;
      if (typeof param === "object") {
        this.setProperties(param);
        return;
      }
      var self = this;
      http.get('/api/v1/content', {id: param}).then(function (result) {
        self.setProperties(result);
      });
    }

    Content.prototype.setProperties = function (obj) {
      this.replies = [];
      if(obj.replies !==undefined && obj.replies.forEach !== undefined){
        var self = this;
        obj.replies.forEach(function(reply){
          self.replies.push(new Reply(reply));
        });
      }
      this.repliesSize = obj.repliesSize;
      this.writer = obj.writer;
      this.lectureName = obj.lectureName;
      this.lectureId = obj.lectureId;
      this.id = obj.id;
      this.title = obj.title;
      this.body = obj.body;
      if (obj.writeDate !== undefined)
        this.writeDate = new Date(obj.writeDate);
      if (obj.dueDate !== undefined)
        this.dueDate = new Date(obj.dueDate);
      this.type = obj.type;
      this.hits = obj.hits;
      this.likes = obj.likes;
      try {
        this.types = JSON.parse(obj.types);
      } catch (e) {
      }
    };

    Content.prototype.getBodyAsHtml = function () {
      return $sce.trustAsHtml(this.body);
    };

    Content.prototype.save = function () {
      var query = {};
      query.id = this.id;
      query.title = this.title;
      query.body = this.body;
      query.lectureId = this.lectureId;
      query.dueDate = this.dueDate;
      query.type = this.type;
      if (this.id === undefined)
        return http.post('/api/v1/content', query);
      return http.put('/api/v1/content', query);
    };

    Content.prototype.remove = function () {
      if (!confirm("삭제하시겠습니까?"))
        return;
      var self = this;
      http.delete('/api/v1/content', {id: this.id}).then(function () {
        $state.go('lecture', {id: self.lectureId});
      });
    };

    Content.getList = function () {
      return $q(function (resolve) {
        http.get('/api/v1/content/list').then(function (response) {
          var result = [];
          if (response.forEach === undefined)
            return;
          response.forEach(function (each) {
            result.push(new Content(each));
          });
          resolve(result);
        });
      });
    };


    return Content;
  });
