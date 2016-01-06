angular.module('clientApp')
  /* @ngInject */
  .service('rootUser', function (alert, dialog, http, responseCode, $mdSidenav, confirm, $rootScope, $state, pageMove) {
    this.email = "";
    this.password = "";
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
      self.waitingLectures = obj.waitingLectures;
      self.major = obj.major;
      self.enrolledLectures = [];
      self.hostingLectures = [];
      self.lectures = obj.lectures;
      obj.lectures.forEach(function (lecture) {
        if (lecture.hostUser.id === self.id) {
          self.hostingLectures.push(lecture);
          return;
        }
        self.enrolledLectures.push(lecture);
      });
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
        $mdSidenav('left').close();
        $rootScope.$broadcast('userStateChange');
      }, function (response) {
        if (response.code === responseCode.Login.NOT_EXIST_EMAIL) {
          alert.error('가입하지 않은 이메일입니다.');
          return;
        }
        if (response.code === responseCode.Login.WRONG_PASSWORD) {
          alert.error('패스워드가 다릅니다.');
        }
        if (response.code === responseCode.Login.DO_EMAIL_VERIFY_FIRST) {
          alert.error('이메일 인증을 해야 합니다. 메일을 확인해주세요.');
        }
      });
    };

    this.register = function () {
      return http.post('/api/v1/user',
        {email: self.email, password: self.password, name: self.name}).then(function () {
        alert.success('메일을 확인해주세요.');
        dialog.login();
      }, function (response) {
        if (response.code === responseCode.Register.ALREADY_EXIST_EMAIL) {
          alert.error('이미 가입한 이메일입니다.');
        }

        if (response.code === responseCode.Register.ACCOUNT_IS_WAITING_FOR_EMAIL_CERTIFY) {
          alert.error('이메일 인증을 기다리고 있는 계정입니다. 먼저 가입을 시도한 회원님이 이메일 인증을 1일내로 진행하지 않을 경우 입력하신 이메일은 누구나 가입할 수 있는 상태로 변경됩니다.');
        }
      });
    };

    this.logout = function () {
      confirm("로그아웃 하시겠습니까?", undefined, function () {
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
          pageMove.ok = true;
          $state.go('loginneed');
          dialog.login();
        });
      });
    };

    this.update = function (obj) {
      if (obj.email !== undefined)
        this.email = obj.email;
      if (obj.profileUrl !== undefined)
        this.profileUrl = obj.profileUrl;
      if (obj.name !== undefined)
        this.name = obj.name;
      if (obj.studentId !== undefined)
        this.studentId = obj.studentId;
      if (obj.introduce !== undefined)
        this.introduce = obj.introduce;
      if (obj.phoneNumber !== undefined)
        this.phoneNumber = obj.phoneNumber;
      if (obj.major !== undefined)
        this.major = obj.major;

      var query = {};
      query.id = this.id;
      query.email = this.email;
      query.profileUrl = this.profileUrl;
      query.name = this.name;
      query.studentId = this.studentId;
      query.introduce = this.introduce;
      query.phoneNumber = this.phoneNumber;
      query.major = this.major;

      http.put('/api/v1/user', query).then(function () {
        alert.success("회원 정보가 수정되었습니다.");
      });
    };

    function getSessionUser() {
      return http.get('/api/v1/user/session').then(function (result) {
        self.setProperties(result);
      });
    }

  });
