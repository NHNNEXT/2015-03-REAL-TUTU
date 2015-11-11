angular.module('clientApp').controller('userController', function ($scope, regex, user, alert, userBroker, dialog, responseCode) {
  $scope.user = user;

  $scope.login = function (user) {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    userBroker.login(user).then(function (result) {
      alert.success('로그인 되었습니다.');
      angular.copy(result, user);
      dialog.cancel();
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

  $scope.register = function (user) {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    userBroker.register(user).then(function () {
      alert.success('가입되었습니다.');
      dialog.login();
    }, function (response) {
      if (response.code === responseCode.Register.ALREADY_EXIST_EMAIL) {
        alert.error('이미 가입한 이메일입니다.');
      }
    });
  };
});

