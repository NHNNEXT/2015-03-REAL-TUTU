angular.module('clientApp')
  .service('userBroker', function (user, http, $state) {
    this.login = function () {
      http.post('/api/v1/user/login', user, function (result) {
        angular.copy(user, result);
        user.logged = true;
        $state.go('main');
      });
    };

    this.register = function (user) {
      http.post('/api/v1/user', user, function () {
        $state.go('login');
      });
    };

    this.logout = function () {
      http.get('/api/v1/user/logout');
      angular.copy({}, user);
      $state.go('main');
    };

    this.getSessionUser = function () {
      http.get('/api/v1/user', {}, function (result) {
        angular.copy(result, user);
        user.logged = true;
      });
    };
  });
