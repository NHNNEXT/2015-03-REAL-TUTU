angular.module('clientApp')

  /* @ngInject */
  .service('dialog', function ($mdDialog, $window) {


    this.report = function () { //[TODO] 향후 출시(?) 정식 서비스시 수정
      $window.open("https://docs.google.com/spreadsheets/d/12tAaAAOSw02veZ8CC_i16be3Msg_RfrBQZ38HIIe3vA/edit?usp=sharing", '_blank');
    };



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


    this.close = function () {
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
          if (result) {
            dialog[result[1]]($parse(result[2])(s));
            return;
          }

          dialog[a.dialog]();
        });
      }
    };
  });
