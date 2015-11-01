
/**
 * @ngdoc service
 * @name clientApp.lectureSvc
 * @description
 * # lectureSvc
 * Service in the clientApp.
 */
 (function() {
   'use strict';

angular
  .module('clientApp')
  .service('modal', modal);
  /* @ngInject */
  function modal($uibModal,$q) {
    this.open = open;

    //"" , "lg", "sm"
    function open(size, templateId, controller,params) {
console.log("create");
      var modalInstance = $uibModal.open({
        templateUrl: templateId,
        controller: controller,
        size:size,
        resolve: {
          params: function () {
            return params;
          }
        }
      });

      var deferred = $q.defer();
      modalInstance.result.then(function(result) {
        deferred.resolve(result);
      },function (error) {
        deferred.reject(error);
      });
      return deferred.promise;
    }
  }
}());
