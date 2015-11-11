'use strict';

angular.module('clientApp')
  .factory('user', function (alert, dialog) {
    var User = function () {
      this.email = "test1@test.com";
      this.password = "password";
    };

    User.prototype.loginCheck = function () {
      if (this.isLogged())
        return true;
      alert.warning("로그인이 필요한 서비스입니다.");
      dialog.login();
    };

    User.prototype.isLogged = function () {
      return this.id !== undefined;
    };
    return new User();
  });
