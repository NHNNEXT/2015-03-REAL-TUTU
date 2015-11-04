'use strict';

/**
 * @ngdoc service
 * @name clientApp.user
 * @description
 * # user
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('user', function () {
    //test계정
    this.email = "test1@test.com";
    this.password = "password";
  });
