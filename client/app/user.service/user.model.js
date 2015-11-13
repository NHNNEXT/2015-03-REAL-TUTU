angular.module('clientApp')
  .factory('User', function () {
    var User = function (obj) {
      this.email = obj.email;
      this.password = obj.password;
    };
    return User;
  });
