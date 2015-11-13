angular.module('clientApp')
  /* @ngInject */
  .service('userBroker', function (rootUser, http, dialog) {
    this.login = function () {
      return http.post('/api/v1/user/login', rootUser);
    };

    this.register = function (user) {
      return http.post('/api/v1/user', user);
    };

    this.logout = function () {
      http.get('/api/v1/user/logout').then(function () {
        angular.copy({}, rootUser);
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
  function (userBroker, rootUser) {
    userBroker.getSessionUser().then(function (result) {
      angular.copy(result, rootUser);
    });
  });
