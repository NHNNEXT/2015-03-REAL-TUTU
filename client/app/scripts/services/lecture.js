/**
 * @ngdoc service
 * @name clientApp.lecture
 * @description
 * # lecture
 * Factory in the clientApp.
 * @author hwangjjung
 */

 // info: 전역에 함수가 생성되지 않도록 IIFE 패턴으로 만듬.
(function() {
  'use strict';

  angular.module('clientApp')
    .factory('lectures', Lectures);

    /* @ngInject */
     function  Lectures(Restangular) {
       var model = Restangular.all('lectures');
       model.one = function(id) {
         return Restangular.one('lectures',id);
       };
       return model;
    }
}());
