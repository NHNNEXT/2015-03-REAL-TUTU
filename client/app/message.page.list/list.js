angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('messages', {
      url: "/messages",
      templateUrl: "/message.page.list/list.html"
    });
});
