'use strict';

angular.module('clientApp')
  .factory('user', function (alert, modal) {
    var User = function () {
      this.email = "test1@test.com";
      this.password = "password";
    };
    User.prototype.loginCheck = function () {
      if (this.logged)
        return true;
      alert.warning("로그인이 필요한 서비스입니다.");
      modal.login();
    };
    return new User();
  });
