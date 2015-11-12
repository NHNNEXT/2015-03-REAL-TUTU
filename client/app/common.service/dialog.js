angular.module('clientApp')

  /* @ngInject */
  .service('dialog', function ($mdDialog) {
    this.login = function (ev) {
      $mdDialog.show({
        controller: 'authController',
        templateUrl: '/user.template/login.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose: true
      });
    };

    this.register = function (ev) {
      $mdDialog.show({
        controller: 'authController',
        templateUrl: '/user.template/register.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose: true
      });
    };

    this.cancel = function() {
      $mdDialog.cancel();
    };
  })
  /* @ngInject */
  .directive('dialog', function (dialog) {
    return {
      restrict: 'A',
      link: function (s, e, a) {
        e.bind('click', function () {
          dialog[a.dialog]();
        });
      }
    };
  });
