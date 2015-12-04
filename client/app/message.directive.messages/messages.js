angular.module('clientApp')
  .directive('messages', function () {
    return {
      restrict: 'E',
      templateUrl: '/message.directive.messages/messages.html',
      /* @ngInject */
      controller: function (Message, $scope, $interval, rootUser, $location) {
        getMessages();

        $interval(getMessages, 10000);
        function getMessages() {
          if (!rootUser.id)
            return;
          Message.getList(0).then(function (messages) {
            $scope.messages = messages;
            $scope.new = 0;
            messages.forEach(function (message) {
              if (message.checked)
                return;
              $scope.new++;
            });
          });
        }

        $scope.moveTo = function (message) {
          message.reading();
          $location.path(message.url);
        };

      }
    };
  });
