/**
 * @ngdoc service
 * @name clientApp.lectureSvc
 * @description
 * # lectureSvc
 * Service in the clientApp.
 */
(function () {
  'use strict';

  angular
    .module('clientApp')
    .service('modal', modal)
    .directive('modal', function (modal) {
      return {
        restrict: 'A',
        link: function (s, e, a) {
          e.bind('click', function () {
            modal[a.modal]();
          })
        }
      }
    });
  /* @ngInject */
  function modal($uibModal, $q) {
    this.open = open;
    var modalInstance;

    this.login = function () {
      open('centered', "/user/modal/login.html", 'userLoginController');
    };

    this.register = function () {
      open('centered', "/user/modal/register.html", 'userLoginController');
    };

    this.close = function(){
      if(modalInstance)
        modalInstance.close();
    };
    //"" , "lg", "sm"
    function open(size, templateId, controller, params) {
      if(modalInstance)
        modalInstance.close();
      modalInstance = $uibModal.open({
        templateUrl: templateId,
        controller: controller,
        size: size,
        resolve: {
          params: function () {
            return params;
          }
        }
      });

      var deferred = $q.defer();
      modalInstance.result.then(function (result) {
        deferred.resolve(result);
      }, function (error) {
        deferred.reject(error);
      });
      return deferred.promise;
    }
  }
}());
