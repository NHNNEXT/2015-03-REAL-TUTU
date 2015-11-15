angular.module('clientApp')
  .controller('messagesController',
  /* @ngInject */
  function ($scope, Message, Page) {

    var page = $scope.page = new Page();

    $scope.more = function () {
      Message.getList(page.next()).then(function (messages) {
        page.return(messages.length);
        messages.forEach(function (message) {
          if ($scope.messages === undefined)
            $scope.messages = [];
          $scope.messages.push(message);
        });
      });
    };

    $scope.more();

  });

