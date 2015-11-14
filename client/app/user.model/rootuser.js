angular.module('clientApp')
  /* @ngInject */
  .service('rootUser', function (alert, dialog, http, responseCode) {
    this.email = "test1@test.com";
    this.password = "password";
    getSessionUser();

    var self = this;

    this.setProperties = function (obj) {
      if (obj === undefined)
        return;
      if (typeof obj !== "object")
        return;
      self.id = obj.id;
      self.email = obj.email;
      self.profileUrl = obj.profileUrl;
      self.name = obj.name;
      self.studentId = obj.studentId;
      self.introduce = obj.introduce;
      self.phoneNumber = obj.phoneNumber;
      self.major = obj.major;
      self.lectures = obj.lectures;
    };

    this.loginCheck = function () {
      if (self.isLogged())
        return true;
      alert.warning("로그인이 필요한 서비스입니다.");
      dialog.login();
    };

    this.isLogged = function () {
      return self.id !== undefined;
    };

    this.login = function () {
      http.post('/api/v1/user/login', {email: self.email, password: self.password}).then(function (result) {
        alert.success('로그인 되었습니다.');
        self.setProperties(result);
        dialog.close();
      }, function (response) {
        if (response.code === responseCode.Login.NOT_EXIST_EMAIL) {
          alert.error('가입하지 않은 아이디입니다.');
          return;
        }
        if (response.code === responseCode.Login.WRONG_PASSWORD) {
          alert.error('패스워드가 다릅니다.');
        }
      });
    };

    this.register = function () {
      return http.post('/api/v1/user',
        {email: self.email, password: self.password, name: self.name}).then(function () {
          alert.success('가입되었습니다.');
          dialog.login();
        }, function (response) {
          if (response.code === responseCode.Register.ALREADY_EXIST_EMAIL) {
            alert.error('이미 가입한 이메일입니다.');
          }
        });
    };

    this.logout = function () {
      http.get('/api/v1/user/logout').then(function () {
        delete self.id;
        delete self.email;
        delete self.profileUrl;
        delete self.name;
        delete self.studentId;
        delete self.introduce;
        delete self.phoneNumber;
        delete self.major;
        delete self.lectures;
        dialog.login();
      });
    };

    this.update = function () {
      http.put('/api/v1/user', this, true).then(function (result) {
        alert.success("회원 정보가 수정되었습니다.");
      });
    };

    function getSessionUser() {
      return http.get('/api/v1/user').then(function (result) {
        self.setProperties(result);
      });
    }

  });
