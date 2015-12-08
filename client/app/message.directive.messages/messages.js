angular.module('clientApp')
  .directive('messages', function () {
    return {
      restrict: 'E',
      templateUrl: '/message.directive.messages/messages.html',
      /* @ngInject */
      controller: function (Message, rootUser, $timeout, $scope) {
        $timeout(getMessages);
        $scope.messages = [];

        $scope.$watch('messages', function () {
          $scope.new = 0;
          $scope.messages.forEach(function (message) {
            if (message.checked)
              return;
            $scope.new++;
          });
        }, true);

        function getMessages() {
          if (!rootUser.id)
            return;
          Message.getList(0).then(function (messages) {
            $timeout(getMessages, 10000);
            if (angular.equals($scope.messages, messages))
              return;
            $scope.messages = messages;
          });
        }
      }
    };
  });
