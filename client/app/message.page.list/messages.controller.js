angular.module('clientApp')
  .controller('messagesController',
  /* @ngInject */
  function ($scope, Message) {
    Message.getList().then(function(messages){
      $scope.messages = messages;
    });

  });

