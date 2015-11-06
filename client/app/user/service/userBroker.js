angular.module('clientApp')
  .service('userBroker', function (user, http, $state, responseCode, alert, $q) {
    this.login = function () {
      http.post('/api/v1/user/login', user).then(function (result) {
        alert.success('로그인 되었습니다.');
        angular.copy(result, user);
        user.logged = true;
        $state.go('main');
      }, function (response) {
        if (response.code === responseCode.Login.NOT_EXIST_EMAIL) {
          alert.error('가입하지 않은 아이디입니다.');
          return;
        }
        if (response.code === responseCode.Login.WRONG_PASSWORD) {
          alert.warning('패스워드가 다릅니다.');
        }
      });
    };

    this.register = function (user) {
      http.post('/api/v1/user', user).then(function () {
        alert.success('가입되었습니다.');
        $state.go('login');
      }, function (response) {
        if (response.code === responseCode.Register.ALREADY_EXIST_EMAIL) {
          alert.info('이미 가입한 이메일입니다.');
          return;
        }
      });
    };

    this.logout = function () {
      http.get('/api/v1/user/logout');
      angular.copy({}, user);
      $state.go('main');
    };

    this.getSessionUser = function () {
      http.get('/api/v1/user').then(function (result) {
        angular.copy(result, user); // 있으면 카피하고 로그온 설정
        user.logged = true;
      });
    };

    this.update = function (user) {
      http.put('/api/v1/user', user).then(function (result) {
        alert.success("회원 정보가 수정되었습니다.");
        angular.merge(user, result);
        user.logged = true;
        //바인딩이 끊어져서 할수없이 강제 입력;
        angular.element($('my-profile')).scope().user = user;
      });
    };


    this.findById = function (id) {
      return http.get('/api/v1/user', {id: id});
    };

  }).run(function (userBroker) {
    userBroker.getSessionUser();
  });
