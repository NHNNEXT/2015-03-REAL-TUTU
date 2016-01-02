angular.module('clientApp')

  /* @ngInject */
  .service('dialog', function ($mdDialog) {

    var self = this;
    this.letter = function (param) {
      self.param = param;
      $mdDialog.show({
        controller: 'letterSendController',
        templateUrl: '/letter/letter.send.html',
        parent: angular.element(document.body),
        clickOutsideToClose: true
      });
    };


    this.login = function (ev) {
      $mdDialog.show({
        controller: 'authController',
        templateUrl: '/user.dialog/login.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose: true
      });
    };

    this.loginHelp = function (param) {
      $mdDialog.show({
        controller: 'loginHelpController',
        templateUrl: '/user.dialog/login-help.html',
        parent: angular.element(document.body),
        clickOutsideToClose: true
      });
      self.param = param;
    };

    this.register = function (ev) {
      $mdDialog.show({
        controller: 'authController',
        templateUrl: '/user.dialog/register.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose: true
      });
    };


    this.report = function (ev) {
      $mdDialog.show({
        controller: 'reportController',
        templateUrl: '/report.dialog/report.html',
        parent: angular.element(document.body),
        targetEvent: ev,
        clickOutsideToClose: true
      });
    };

    this.close = function() {
      $mdDialog.cancel();
    };
  })
  /* @ngInject */
  .directive('dialog', function (dialog, $parse) {
    return {
      restrict: 'A',
      link: function (s, e, a) {
        var regex = /^(\w+)\((.*)\)$/i;
        e.bind('click', function () {
          var result = regex.exec(a.dialog);
          if(result){
            dialog[result[1]]($parse(result[2])(s));
            return;
          }

          dialog[a.dialog]();
        });
      }
    };
  });
