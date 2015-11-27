angular.module('clientApp')
  /* @ngInject */
  .factory('Content', function (http, $sce, $q, $state, confirm) {
    function Content(param) {
      if (param === undefined)
        return;
      if (typeof param === "object") {
        this.setProperties(param);
      }
    }

    Content.findById = function (id) {
      return $q(function (resolve) {
        http.get('/api/v1/content', {id: id}).then(function (result) {
          resolve(new Content(result));
        });
      });
    };

    Content.prototype.setProperties = function (obj) {
      this.submitRequiredUsers = obj.submitRequiredUsers;
      this.repliesSize = obj.repliesSize;
      this.writer = obj.writer;
      this.lectureName = obj.lectureName;
      this.lectureId = obj.lectureId;
      this.id = obj.id;
      this.title = obj.title;
      this.tags = obj.tags === undefined ? [] : obj.tags;
        this.body = obj.body;
      if (obj.writeDate)
        this.writeDate = new Date(obj.writeDate);
      if (obj.startTime)
        this.startTime = new Date(obj.startTime);
      if (obj.endTime)
        this.endTime = new Date(obj.endTime);
      this.type = obj.type; //타입오브젝트
      this.hits = obj.hits;
      this.likes = obj.likes;
    };

    Content.prototype.getBodyAsHtml = function () {
      return $sce.trustAsHtml(this.body);
    };

    Content.prototype.getQuery = function () {
      var query = {};
      query.id = this.id;
      query.title = this.title;
      query.body = this.body;
      query.lectureId = this.lectureId;
      query.endTime = this.endTime;
      query.startTime = this.startTime;
      if (this.id === undefined)
        query.type = this.type.id;
      if (this.type.submit) {
        query.submitRequiredUsers = [];
        this.users.forEach(function (user) {
          if (user.submit)
            query.submitRequiredUsers.push(user.id);
        });
      }
      return query;
    };

    Content.prototype.setType = function (type) {
      this.type = type;
    };

    Content.prototype.save = function () {
      var query = this.getQuery();
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
