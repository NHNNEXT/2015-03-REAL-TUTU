angular.module('clientApp')
  .directive('messages', function () {
    return {
      restrict: 'E',
      templateUrl: '/message.directive.messages/messages.html',
      /* @ngInject */
      controller: function (Message, $scope, $interval, rootUser, $timeout) {
        $timeout(getMessages);
        $interval(getMessages, 10000);
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
            if (angular.equals($scope.messages, messages))
              return;
            $scope.messages = messages;
          });
        }
      }
    };
  });
