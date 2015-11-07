angular.module('clientApp')
  .service('userBroker', function (user, http, modal, responseCode, alert, $q) {
    this.login = function () {
      return http.post('/api/v1/user/login', user);
    };

    this.register = function (user) {
      return http.post('/api/v1/user', user);
    };

    this.logout = function () {
      http.get('/api/v1/user/logout').then(function(){
        angular.copy({}, user);
        modal.login();
      });
    };

    this.getSessionUser = function () {
      return http.get('/api/v1/user');
    };

    this.update = function (user) {
      return http.put('/api/v1/user', user);
    };

    this.findById = function (id) {
      return http.get('/api/v1/user', {id: id});
    };

  }).run(function (userBroker, user) {
    userBroker.getSessionUser().then(function (result) {
      angular.copy(result, user); // 있으면 카피하고 로그온 설정
      user.logged = true;
    });
  });
