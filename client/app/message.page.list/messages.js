/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('messages', {
      header: "새 소식",
      url: "/messages",
      controller: 'messagesController',
      templateUrl: "/message.page.list/messages.html"
    });
});
