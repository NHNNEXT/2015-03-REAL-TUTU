/* @ngInject */
angular.module('clientApp').controller('authController', function ($scope, rootUser, alert, confirm, http) {
  $scope.rootUser = rootUser;

  $scope.login = function () {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    rootUser.login();
  };

  $scope.register = function () {
    if ($scope.loginForm.$invalid) {
      alert.warning('정보를 입력해주세요.');
      return;
    }
    rootUser.register();
  };

  $scope.passwordResetRequest = function () {
    confirm("비밀번호 재설정 메일을 받습니다.", "요청 이메일 : " + rootUser.email, function () {
      http.post('/api/v1/user/passwordReset', {email: rootUser.email}).then(function () {
        alert.success("메일이 발송되었습니다. 확인해주세요.");
      });
    });
  };

});

