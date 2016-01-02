/* @ngInject */
angular.module('clientApp').controller('loginHelpController',
  function ($scope, rootUser, alert, confirm, http, responseCode, dialog) {
    $scope.rootUser = rootUser;

    $scope.btn = {};
    $scope.btn.email = "인증메일을 다시 받습니다.";
    $scope.btn.password = "패스워드 변경 메일을 받습니다.";

    $scope.action = {};
    $scope.action.email = email;
    $scope.action.password = password;
    $scope.helpType = dialog.param;

    function password() {
      if (!/^.+@.+\..+$/.test(rootUser.email)) {
        alert.warning("이메일을 형식에 맞게 입력해주세요.");
        return;
      }
      confirm("비밀번호 재설정 메일을 받습니다.", "요청 이메일 : " + rootUser.email, function () {
        http.post('/api/v1/user/sendChangePwMail', {email: rootUser.email}).then(function () {
          alert.success("메일이 발송되었습니다. 확인해주세요.");
        }, function (res) {
          if (res.code === responseCode.MailRequest.USER_NOT_EXIST) {
            alert.warning("가입하지 않은 계정입니다.");
            dialog.loginHelp('password');
          }
          if (res.code === responseCode.MailRequest.USER_NOT_VERIFIED) {
            alert.warning("아직 인증받지 않은 계정입니다. 인증을 먼저 진행해 주세요.");
            dialog.loginHelp('email');
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
          if (res.code === responseCode.MailRequest.USER_NOT_EXIST) {
            alert.warning("가입하지 않은 계정입니다.");
            dialog.loginHelp('email'); // 메일주소를 잘못 입력한 경우라고 가정.
            return;
          }
          if (res.code === responseCode.MailRequest.USER_ALREADY_VERIFIED) {
            alert.warning("이미 인증받은 계정입니다. 로그인해주세요.");
            dialog.login();
          }
        });
      });
    }

  });


