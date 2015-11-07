angular.module('clientApp').controller('userLoginController', function ($scope, regex, user, alert, userBroker, modal, responseCode) {
  $scope.regex = regex;
  $scope.user = user;
  $scope.valid = function () {
    if (!$scope.ngRegex)
      return;
    return $scope.ngRegex['user.email'] && $scope.ngRegex['user.password'];
  };

  $scope.validName = function () {
    if (!$scope.ngRegex)
      return;
    return $scope.ngRegex['user.name'];
  };

  $scope.login = function (user) {
    if (!$scope.valid()) {
      alert.warning('이메일과 패스워드를 입력해 주세요');
      return;
    }
    userBroker.login(user).then(function (result) {
      alert.success('로그인 되었습니다.');
      angular.copy(result, user);
      user.logged = true;
      modal.close();
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

  $scope.register = function (user) {
    if (!$scope.valid() || !$scope.validName()) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    userBroker.register(user).then(function () {
      alert.success('가입되었습니다.');
      modal.close();
    }, function (response) {
      if (response.code === responseCode.Register.ALREADY_EXIST_EMAIL) {
        alert.info('이미 가입한 이메일입니다.');
      }
    });
  };
});

