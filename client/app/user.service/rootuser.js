angular.module('clientApp')
  .factory('rootUser', function (alert, dialog) {
    var rootUser = function () {
      this.email = "test1@test.com";
      this.password = "password";
    };
    rootUser.prototype.loginCheck = function () {
      if (this.isLogged())
        return true;
      alert.warning("로그인이 필요한 서비스입니다.");
      dialog.login();
    };
    rootUser.prototype.isLogged = function () {
      return this.id !== undefined;
    };

    return new rootUser();
  });
