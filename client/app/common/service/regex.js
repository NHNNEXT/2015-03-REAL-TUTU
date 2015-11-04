'use strict';

/**
 * @ngdoc service
 * @name clientApp.regex
 * @description
 * # regex
 * Service in the clientApp.
 */
angular.module('clientApp')
  .service('regex', function () {
    this.email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
    this.password = /^\S{6,12}$/;
    this.name = /^\S{2,12}$/;
  });
