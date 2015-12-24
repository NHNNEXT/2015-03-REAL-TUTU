angular.module('clientApp')
  /* @ngInject */
  .factory('User', function (http, $q, rootUser, Content) {
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
      this.group = obj.group;
      this.lectures = obj.lectures;
      if (obj.writeContents)
        this.writeContents = obj.writeContents.map(function (content) {
          return new Content(content);
        });
      if (obj.likeContents)
        this.likeContents = obj.likeContents.map(function (content) {
          return new Content(content);
        });
    };

    User.prototype.isRootUser = function () {
      if (!rootUser.isLogged())
        return;
      return rootUser.id === this.id;
    };

    User.findOne = function (query) {
      return $q(function (resolve) {
        http.get('/api/v1/user', query).then(function (result) {
          resolve(new User(result));
        });
      });
    };

    return User;
  });
