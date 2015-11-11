angular.module('clientApp')
  .service('dialog', function ($mdDialog) {

    this.login = function (ev) {
      $mdDialog.show({
        controller: 'userController',
        templateUrl: '/user/auth/login.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose: true
      })
        .then(function (answer) {
        }, function () {
        });
    };

    this.register = function (ev) {
      $mdDialog.show({
        controller: 'userController',
        templateUrl: '/user/auth/register.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose: true
      })
        .then(function (answer) {
        }, function () {
        });
    };

    this.cancel = function() {
      $mdDialog.cancel();
    };
  }).directive('dialog', function (dialog) {
    return {
      restrict: 'A',
      link: function (s, e, a) {
        e.bind('click', function () {
          dialog[a.dialog]();
        })
      }
    }
  });
