angular.module('clientApp')
  /* @ngInject */
  .factory('Lecture',
    function (http, $state, confirm, User, Content, $q, rootUser, alert, responseCode, $rootScope) {
      function Lecture(param) {
        if (param === undefined) {
          this.contentGroups = [{
            "submitOpen": false,
            "reply": true,
            "name": "수업",
            "contentType": "SCHEDULE"
          }, {
            "submitOpen": false,
            "reply": true,
            "name": "강의자료",
            "attachment": true,
            "contentType": "GENERAL"
          }, {
            "submitOpen": false,
            "reply": true,
            "name": "공지",
            "contentType": "NOTICE"
          }, {
            "submitOpen": false,
            "reply": true,
            "name": "과제",
            "contentType": "SUBMIT"
          }
          ];
          this.userGroups = [{"defaultGroup": false, "name": "조교"}, {"defaultGroup": true, "name": "수강생"}];
          this.writable = [[true, true, true, true], [false, false, true, true]];
          this.readable = [[true, true, true, true], [true, true, true, true]];
          this.submitReadable = [[false, false, false, true], [false, false, false, false]];
          this.defaultGroupDefine();
          return;
        }
        if (typeof param === "object") {
          this.setProperties(param);
        }
      }


      Lecture.prototype.isEnrolled = function () {
        var lectures = rootUser.lectures;
        if (!lectures)
          return false;
        for (var i = 0; i < lectures.length; i++)
          if (lectures[i].id === this.id)
            return true;
        return false;
      };

      Lecture.prototype.isWaiting = function () {
        var lectures = rootUser.waitingLectures;
        if (!lectures)
          return false;
        for (var i = 0; i < lectures.length; i++)
          if (lectures[i].id === this.id)
            return true;
        return false;
      };

      Lecture.prototype.removeUserGroup = function (el) {
        if (this.userGroups.length < 2) {
          alert.warning("최소 하나의 그룹은 있어야 합니다.");
          return;
        }
        if (this.defaultGroup === this.userGroups.indexOf(el)){
          alert.warning("기본 그룹은 삭제할 수 없습니다.");
          return;
        }
        var self = this;
        confirm("삭제하시겠습니까?", "그룹 : " + el.name + "<br><br> 그룹에 속한유저는 모두 기본그룹으로 옮겨집니다.", function () {
          self.userGroups.remove(el);
          if (self.userGroups[self.defaultGroup] === undefined)
            self.defaultGroup = 0;
        });
      };

      Lecture.prototype.removeContentGroup = function (el) {
        if (this.contentGroups.length < 2) {
          alert.warning("최소 하나의 유형은 있어야 합니다.");
          return;
        }
        var self = this;
        confirm("삭제하시겠습니까?", "유형 : " + el.name + "<br><br> 그룹에 속한 컨텐츠도 모두 삭제됩니다.", function () {
          self.contentGroups.remove(el);
        });
      };

      Lecture.prototype.setProperties = function (param) {
        this.id = param.id;
        this.name = param.name;
        this.majorType = param.majorType;
        this.registerPolicy = param.registerPolicy;
        this.likes = param.likes;
        this.contentLength = param.contentLength;
        this.scheduleLength = param.scheduleLength;
        this.hostUser = new User(param.hostUser);
        this.contentGroups = param.contentGroups;
        this.userGroups = param.userGroups;
        this.users = param.users;
        this.waitingUsers = param.waitingUsers;
        this.writable = param.writable;
        this.readable = param.readable;
        this.submitReadable = param.submitReadable;
        this.defaultGroupDefine();
      };

      Lecture.prototype.defaultGroupDefine = function () {
        var self = this;
        if (!this.userGroups)
          return;
        this.userGroups.forEach(function (userGroup, index) {
          if (userGroup.defaultGroup)
            self.defaultGroup = index;
        });

      };

      Lecture.prototype.remove = function () {
        var self = this;
        confirm("강의를 닫겠습니까?", "강의 : " + self.name, function () {
          http.delete('/api/v1/lecture', {id: self.id}).then(function () {
            rootUser.lectures.removeById(self.id);
            rootUser.hostingLectures.removeById(self.id);
            $state.go('lectures');
          });
        });
      };

      Lecture.prototype.enroll = function () {
        http.post('/api/v1/lecture/enroll', {id: this.id}).then(function () {
          alert.info('등록되었습니다.');
        }, function (response) {
          if (response.code === responseCode.Enroll.WAITING_FOR_APPROVAL) {
            alert.info('참가 요청하였습니다. 승인을 기다립니다.');
            rootUser.waitingLectures.push(response.result);
          }
        });
      };

      Lecture.prototype.save = function () {
        var query = {};
        query.id = this.id;
        query.name = this.name;
        query.majorType = this.majorType;
        query.registerPolicy = this.registerPolicy;
        query.contentGroups = this.contentGroups;
        var self = this;
        this.userGroups.forEach(function (userGroup, index) {
          userGroup.defaultGroup = index === self.defaultGroup;
        });
        query.userGroups = this.userGroups;
        query.writable = this.writable;
        query.readable = this.readable;
        query.submitReadable = this.submitReadable;
        if (this.id === undefined)
          return http.post('/api/v1/lecture', query, true);
        return http.put('/api/v1/lecture', query, true);
      };

      Lecture.prototype.approval = function (user) {
        var self = this;
        confirm("승인하시겠습니까?", user.name + "님의 가입 요청을 승인합니다.", function () {
          var query = {};
          query.id = self.id;
          query.userId = user.id;
          http.post('/api/v1/lecture/approval', query).then(function (result) {
            alert.success("가입 승인되었습니다.");
            var approved = self.waitingUsers.findById(result.id);
            self.waitingUsers.remove(approved);
            self.users.push(result);
          });
        });
      };

      Lecture.prototype.reject = function (user) {
        var self = this;
        confirm("거절하시겠습니까?", user.name + "님의 가입 요청을 거절합니다.", function () {
          var query = {};
          query.id = self.id;
          query.userId = user.id;
          http.post('/api/v1/lecture/reject', query).then(function () {
            alert.info("가입 신청을 거절하였습니다.");
            self.waitingUsers.removeById(user.id);
          });
        });
      };

      Lecture.prototype.expel = function (user) {
        if (this.hostUser.id === user.id) {
          alert.error("개설자는 탈퇴할 수 없습니다.");
          return;
        }
        var message;
        var self = this;
        var isRootUser = user.id === rootUser.id;
        var approved = self.users.findById(user.id);
        if (isRootUser) {
          if (approved)
            message = "강의에서 탈퇴합니다";
          else
            message = "가입 신청을 취소합니다.";
        }
        else
          message = "강의에서 " + user.name + "님을 탈퇴시킵니다.";

        confirm(message, undefined, function () {

          http.post('/api/v1/lecture/expel', {lectureId: self.id, userId: user.id}).then(function () {
            if (isRootUser) {
              if (!approved) {
                alert.success("가입 신청을 취소했습니다.");
                rootUser.waitingLectures.removeById(self.id);
                $rootScope.$broadcast('userStateChange');
                return;
              }
              alert.success("강의에서 탈퇴했습니다.");
              rootUser.lectures.removeById(self.id);
              $rootScope.$broadcast('userStateChange');
              return;
            }
            alert.success("강의에서 " + user.name + "님을 탈퇴 시켰습니다.");
            self.users.removeById(user.id);

          });
        });
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


      Lecture.findById = function (id) {
        return $q(function (resolve) {
          http.get('/api/v1/lecture', {id: id}).then(function (result) {
            resolve(new Lecture(result));
          });
        });
      };

      Lecture.getWriteInfoById = function (id) {
        return $q(function (resolve) {
          http.get('/api/v1/lecture/writeInfo', {id: id}).then(function (result) {
            resolve(new WriteInfo(result));
          });
        });
      };

      function WriteInfo(param) {
        this.id = param.id;
        this.name = param.name;
        this.hostUserId = param.hostUserId;
        this.contentGroups = param.contentGroups;
        this.users = param.users;
      }

      return Lecture;
    });
