angular.module('clientApp')
  /* @ngInject */
  .factory('User', function (http, $q) {
    var User = function (obj) {
      if (obj === undefined)
        return;
      if (typeof obj !== "object") {
        return;
      }
      this.id = obj.id;
      this.email = obj.email;
      this.name = obj.name;
      this.profileUrl = obj.profileUrl;
      this.studentId = obj.studentId;
      this.phoneNumber = obj.phoneNumber;
      this.major = obj.major;
      this.introduce = obj.introduce;
    };

    User.findById = function (id) {
      return $q(function (resolve) {
        http.get('/api/v1/user', {id: id}).then(function (result) {
          resolve(new User(result));
        });
      });
    };

    return User;
  });
