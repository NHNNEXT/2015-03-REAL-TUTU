angular.module('clientApp')
  /* @ngInject */
  .factory('Lecture', function (http, $state, confirm, User, Content, Lesson, $q, rootUser, alert) {
    function Lecture(param) {
      if (param === undefined)
        return;
      if (typeof param === "object") {
        this.setProperties(param);
        return;
      }
      var self = this;
      http.get('/api/v1/lecture', {id: param}).then(function (result) {
        self.setProperties(result);
      });
    }

    Lecture.prototype.setProperties = function (param) {
      this.id = param.id;
      this.name = param.name;
      this.majorType = param.majorType;
      this.likes = param.likes;
      this.date = new Date(param.date);
      this.hostUser = new User(param.hostUser);
      this.types = JSON.parse(param.types);
      var self = this;
      if (param.users.forEach !== undefined) {
        self.users = [];
        param.users.forEach(function (user) {
          self.users.push(new User(user));
        });
      }
      if (param.contents.forEach !== undefined) {
        self.contents = [];
        param.contents.forEach(function (content) {
          self.contents.push(new Content(content));
        });
      }
      if (param.lessons.forEach !== undefined) {
        self.lessons = [];
        param.lessons.forEach(function (lesson) {
          self.lessons.push(new Lesson(lesson));
        });
      }
    };

    Lecture.prototype.remove = function () {
      if (!confirm("삭제하시겠습니까?"))
        return;
      http.delete('/api/v1/lecture', {id: this.id}, true).then(function () {
        $state.go('lectures');
      });
    };

    Lecture.prototype.enroll = function () {
      http.post('/api/v1/lecture/enroll', {id: this.id}, true).then(function () {
        alert.info('강의에 등록되었습니다.');
      });
    };

    Lecture.prototype.save = function () {
      var query = {};
      var managerIds = [];
      this.managers.forEach(function (manager) {
        managerIds.push(manager.id);
      });
      query.lessonString = JSON.stringify(this.lessons);
      query.types = JSON.stringify(this.types);
      query.id = this.id;
      query.name = this.name;
      query.majorType = this.majorType;
      return http.post('/api/v1/lecture', query, true);
    };

    Lecture.getList = function () {
      return $q(function (resolve) {
        http.get('/api/v1/lecture').then(function (response) {
          var result = [];
          if (response.forEach === undefined)
            return;
          response.forEach(function (each) {
            result.push(new Lecture(each));
          });
          resolve(result);
        });
      });
    };

    return Lecture;
  });
