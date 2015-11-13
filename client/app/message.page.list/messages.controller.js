angular.module('clientApp')
  .controller('messagesController',
  /* @ngInject */
  function ($scope, Message) {
    $scope.news = [];

    $scope.news.push(new Message(0));

  });

