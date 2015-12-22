angular.module('clientApp')
  /* @ngInject */
  .factory('Content', function (http, $sce, $q, $state, confirm, ContentGroup, Attachment) {
    function Content(param) {
      if (param === undefined || param === null)
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
      this.body = obj.body;
      this.relativeContents = obj.relativeContents;
      this.attachments = obj.attachments.map(function(attachment){
        return new Attachment(attachment);
      });
      this.submitCanAttach = obj.submitCanAttach;
      this.tags = obj.tags === undefined ? [] : obj.tags;
      if (obj.writeDate)
        this.writeDate = new Date(obj.writeDate);
      if (obj.startTime)
        this.startTime = new Date(obj.startTime);
      if (obj.endTime)
        this.endTime = new Date(obj.endTime);
      if (this.startTime > this.endTime) {
        var tmp = this.startTime;
        this.startTime = this.endTime;
        this.endTime = tmp;
      }
      this.contentGroup = new ContentGroup(obj.contentGroup); //타입오브젝트
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
      query.submitCanAttach = this.submitCanAttach;
      if (this.id === undefined)
        query.contentGroup = this.contentGroup.id;
      if (this.contentGroup.contentType === 'SUBMIT') {
        query.submitRequiredUsers = [];
        this.users.forEach(function (user) {
          if (user.submit)
            query.submitRequiredUsers.push(user.id);
        });
      }
      if (this.attachments) {
        query.attachments = [];
        this.attachments.forEach(function (attachment) {
          query.attachments.push(attachment.id);
        });
      }
      return query;
    };

    Content.prototype.setContentGroup = function (contentGroup) {
      this.contentGroup = contentGroup;
    };

    Content.prototype.save = function () {
      var query = this.getQuery();

      if (this.id === undefined)
        return http.post('/api/v1/content', query, true);

      if (this.contentGroup.contentType !== 'SUBMIT')
        return http.put('/api/v1/content', query, true);

      var removed = this.submitRequiredUsers.filter(function (relation) {
        return !query.submitRequiredUsers.includes(relation.user.id);
      });

      if (removed.length === 0)
        return http.put('/api/v1/content', query, true);

      var names = [];
      removed.forEach(function (relation) {
        names.push(relation.user.name);
      });

      return $q(function (resolve) {
        confirm("제출 대상 제외 확인", names.join("님, ") + "님이 제출 대상에서 제외됩니다.<br><br><h2>기존에 제출했던 자료가 있다면 모두 삭제됩니다.</h2> 계속 진행하시겠습니까?", function () {
          http.put('/api/v1/content', query, true).then(function(response){
            resolve(response);
          });
        });
      });

    };


    Content.prototype.remove = function () {
      var self = this;
      confirm("삭제하시겠습니까?", self.title, function () {
        http.delete('/api/v1/content', {id: self.id}).then(function () {
          $state.go('lecture', {id: self.lectureId});
        });
      });
    };

    Content.getList = function (query) {
      return $q(function (resolve) {
        http.get('/api/v1/content/list', query).then(function (response) {
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

    Content.getListInMyLectures = function (query) {
      return $q(function (resolve) {
        http.get('/api/v1/content/list/my', query).then(function (response) {
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

    Content.getListByDuration = function (query) {
      return $q(function (resolve) {
        http.get('/api/v1/content/list/duration', query).then(function (response) {
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
