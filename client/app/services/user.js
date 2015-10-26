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
    this.name = "황정민";
    this.profile = "/favicon.ico";

    // AngularJS will instantiate a singleton by calling "new" on this function
  });
