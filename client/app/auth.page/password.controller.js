angular.module('clientApp')
  /* @ngInject */
  .controller('passwordRefineController', function ($scope, $state, $stateParams, http, responseCode, alert, dialog, rootUser) {
    $scope.rootUser = rootUser;
    rootUser.email = $stateParams.email;
    $scope.sendPasswordRefineRequest = function () {
      if ($scope.passwordForm.$invalid) {
        alert.warning('패스워드는 6~20자입니다.');
        return;
      }
      var query = {};
      query.key = $stateParams.key;
      query.email = rootUser.email;
      query.password = rootUser.password;
      http.post('/api/v1/user/changePassword', query).then(function () {
        alert.success("패스워드 변경이 완료 되었습니다.");
        $state.go('main');
        dialog.login();
      }, function (result) {
        rootUser.email = $stateParams.email;
        if (result.code === responseCode.PasswordChange.TOO_MANY_TRY) {
          alert.warning("인증 횟수를 초과했습니다. 인증 메일을 다시 요청해주세요.");
          dialog.loginHelp('password');
          return;
        }
        if (result.code === responseCode.PasswordChange.KEY_NOT_MATCHED) {
          alert.warning("유효하지 않은 인증키입니다. 메일을 다시 확인해주세요.");
        }
        if (result.code === responseCode.PasswordChange.EXPIRED) {
          alert.warning("이미 처리되었거나, 없는 요청입니다. 메일을 다시 확인해주세요.");
        }
      });
    };
  });
