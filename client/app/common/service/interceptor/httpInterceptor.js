/**
 * Created by itmnext13 on 2015. 11. 3..
 */
(function () {
  'use strict';

  angular
    .module('clientApp')
    .factory('httpInterceptor',httpInterceptor);

  function httpInterceptor($q) {

    return {
      'request': request,
      'requestError': requestError,
      'response': response,
      'responseError': responseError
    };


    function request(config) {
      return config;
    }

    function requestError(rejection) {
      return $q.reject(rejection);
    }

    function response(response) {
      return response;
    }

    function responseError(rejection) {
      var msg = httpType(rejection.status);
      return $q.reject(rejection);
    }

    function httpType(status){
      var msg = null;
      switch(status) {
        case 400:
          msg = {
            code : 'BAD_REQUEST',
            value : 'You send a Bad Request. send the right thing.'
          };
          break;
        case 401:
          msg = {
            code : 'UNAUTHORIZED',
            value : 'Login Required. Or your login info is expired.'
          };
          break;
        case 403:
          msg = {
            code : 'FORBIDDEN',
            value : 'Your Authorized is forbidden. Request the authorization to administrator.'
          };
          break;
        case 404:
          msg = {
            code : 'FORBIDDEN',
            value : 'Your Authorized is forbidden. Request the authorization to administrator.'
          };
          break;
        case 405:
          msg = {
            code : 'NOT_FOUND',
            value : 'Not found the content.'
          };
          break;
        case 500:
          msg = {
            code : 'SERVER_ERROR',
            value : 'Internal Server Error.'
          };
      }
      return msg;
    }

  }


}());
