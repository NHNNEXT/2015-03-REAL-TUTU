angular.module('clientApp')
  /* @ngInject */
  .factory('User', function (http, $q, rootUser) {
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
      this.writeContents = obj.writeContents;
      if (this.writeContents)
        this.writeContents.forEach(function (each) {
          if (each.writeDate !== undefined)
            each.writeDate = new Date(each.writeDate);
          if (each.startTime !== undefined)
            each.startTime = new Date(each.startTime);
          if (each.endTime !== undefined)
            each.endTime = new Date(each.endTime);
        });
      this.lectures = obj.lectures;
    };

    User.prototype.isRootUser = function () {
      if (!rootUser.isLogged())
        return;
      return rootUser.id === this.id;
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
