'use strict';

/**
 * @ngdoc service
 * @name clientApp.api
 * @description
 * # api
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('api', function (http) {
    this.user = {};
    this.user.login = function (user, success) {
      if (!user)
        return;
      http.post('/api/v1/user/login', user, success);
    };
  });
