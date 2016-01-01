angular.module('clientApp')
  .controller('letterSendController',
    /* @ngInject */
    function (Letter, dialog, $scope, alert) {
      $scope.dialog = dialog;

      $scope.send = function (message, receiver) {
        Letter.send(message, receiver).then(function () {
          alert.success(receiver.name + "님에게 쪽지를 보냈습니다.");
          dialog.close();
        });
      };

    });

