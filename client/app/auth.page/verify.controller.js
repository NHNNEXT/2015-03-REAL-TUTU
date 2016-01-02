angular.module('clientApp')
  /* @ngInject */
  .controller('verifyController', function ($scope, $state, $stateParams, http, responseCode, alert, dialog, rootUser) {
    rootUser.email = $stateParams.email;
    $scope.status = "메일인증이 진행중입니다.";
    http.post('/api/v1/user/mailVerify', $stateParams).then(function () {
      alertStatus('success', "인증되었습니다. 감사합니다.");
      $state.go('main');
      dialog.login();
    }, function (result) {
      if (result.code === responseCode.UserAuth.USER_ALREADY_VERIFIED) {
        alertStatus('info', "이미 인증받은 유저입니다. 로그인해주세요.");
        dialog.login();
        return;
      }
      if (result.code === responseCode.UserAuth.MAIL_EXPIRED) {
        alertStatus('warning', "인증 시간을 초과했습니다. 인증 메일을 다시 요청해주요.");
        dialog.loginHelp('email');
        return;
      }
      if (result.code === responseCode.UserAuth.KEY_NOT_MATCHED) {
        alertStatus('warning', "유효하지 않은 인증키입니다. 메일을 다시 확인해주세요.");
      }
    });

    function alertStatus(method, m) {
      alert[method](m);
      $scope.status = m;
    }
  });
