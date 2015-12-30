/* @ngInject */
angular.module('clientApp').controller('loginHelpController',
  function ($scope, rootUser, alert, confirm, http, responseCode) {
    $scope.rootUser = rootUser;

    $scope.btn = {};
    $scope.btn.email = "인증메일을 다시 받습니다.";
    $scope.btn.password = "패스워드 변경 메일을 받습니다.";

    $scope.action = {};
    $scope.action.email = email;
    $scope.action.password = password;

    function password() {
      if (!/^.+@.+\..+$/.test(rootUser.email)) {
        alert.warning("이메일을 형식에 맞게 입력해주세요.");
        return;
      }
      confirm("비밀번호 재설정 메일을 받습니다.", "요청 이메일 : " + rootUser.email, function () {
        http.post('/api/v1/user/sendChangePwMail', {email: rootUser.email}).then(function () {
          alert.success("메일이 발송되었습니다. 확인해주세요.");
        }, function (res) {
          if(res.code === responseCode.Login.USER_NOT_EXIST) {
            alert.warning("가입하지 않은 계정입니다.");
          }
        });
      });
    }

    function email() {
      if (!/^.+@.+\..+$/.test(rootUser.email)) {
        alert.warning("이메일을 형식에 맞게 입력해주세요.");
        return;
      }
      confirm("인증 메일을 다시 받습니다.", "요청 이메일 : " + rootUser.email, function () {
        http.post('/api/v1/user/resendMailVerify', {email: rootUser.email}).then(function () {
          alert.success("메일이 발송되었습니다. 확인해주세요.");
        }, function (res) {
          if(res.code === responseCode.Login.USER_NOT_EXIST) {
            alert.warning("가입하지 않은 계정입니다.");
          }
        });
      });
    }

  });


