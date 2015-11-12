angular.module('clientApp')
  /* @ngInject */
  .service('userBroker', function (user, http, dialog) {
    this.login = function () {
      return http.post('/api/v1/user/login', user);
    };

    this.register = function (user) {
      return http.post('/api/v1/user', user);
    };

    this.logout = function () {
      http.get('/api/v1/user/logout').then(function () {
        angular.copy({}, user);
        dialog.login();
      });
    };

    this.getSessionUser = function () {
      return http.get('/api/v1/user');
    };

    this.update = function (user) {
      return http.put('/api/v1/user', user, true);
    };

    this.findById = function (id) {
      return http.get('/api/v1/user', {id: id});
    };

  }).run(
  /* @ngInject */
  function (userBroker, user) {
    userBroker.getSessionUser().then(function (result) {
      angular.copy(result, user);
    });
  });
