angular.module('clientApp')
  /* @ngInject */
  .factory('Lecture', function (http, $state, confirm, User, Content, $q, rootUser, alert) {
    function Lecture(param) {
      if (param === undefined) {
        this.userGroups = [{name: "조교"}, {name: "수강생", defaultGroup: true}];
        this.contentTypes = [
          {name: "수업", startTime: true, endTime: true, extendWrite: true},
          {name: "강의자료"}, {name: "질문"},
          {name: "과제", endTime: true, statistic: true, onlyWriter: true}
        ];
        return;
      }
      if (typeof param === "object") {
        this.setProperties(param);
        return;
      }
      var self = this;
      http.get('/api/v1/lecture', {id: param}).then(function (result) {
        self.setProperties(result);
      });
    }

    Lecture.prototype.defaultGroupSelect = function (index, select) {
      this.userGroups.forEach(function (userGroup, i) {
        if (i === index) {
          userGroup.defaultGroup = true;
          select[i] = false;
          return;
        }
        userGroup.defaultGroup = false;
        select[i] = false;
      });
    };

    Lecture.prototype.removeUserGroup = function (el) {
      if (this.userGroups.length < 2) {
        alert.warning("최소 하나의 그룹은 있어야 합니다.");
        return;
      }
      if (!confirm("삭제하시겠습니까?"))
        return;
      this.userGroups.remove(el);
      if (this.userGroups[this.defaultGroup] === undefined)
        this.defaultGroup = 0;
    };

    Lecture.prototype.removeContentType = function (el) {
      if (this.contentTypes.length < 2) {
        alert.warning("최소 하나의 타입은 있어야 합니다.");
        return;
      }
      if (!confirm("삭제하시겠습니까?"))
        return;
      this.contentTypes.remove(el);
    };

    Lecture.prototype.setProperties = function (param) {
      this.id = param.id;
      this.name = param.name;
      this.majorType = param.majorType;
      this.likes = param.likes;
      this.date = new Date(param.date);
      this.hostUser = new User(param.hostUser);
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
    };

    Lecture.prototype.remove = function () {
      if (!confirm("삭제하시겠습니까?"))
        return;
      http.delete('/api/v1/lecture', {id: this.id}).then(function () {
        $state.go('lectures');
      });
    };

    Lecture.prototype.enroll = function () {
      http.post('/api/v1/lecture/enroll', {id: this.id}).then(function () {
        alert.info('강의에 등록되었습니다.');
      });
    };

    Lecture.prototype.save = function (write, read) {
      var query = {};
      query.id = this.id;
      query.name = this.name;
      query.majorType = this.majorType;
      query.registerPolicyType = this.registerPolicyType;
      query.contentTypes = this.contentTypes;
      query.userGroups = this.userGroups;
      query.write = write;
      query.read = read;
      if (query.id === undefined)
        return http.post('/api/v1/lecture', query, true);
      return http.put('/api/v1/lecture', query, true);
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
