/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('loginneed', {
      url: "/loginneed",
      templateUrl: "/common.page.loginneed/loginneed.html",
      header: "로그인이 필요합니다!",
      controller: function ($scope, rootUser, $state) {
        $scope.$watch(function () {
          return rootUser.id;
        }, function (id) {
          if (id === undefined)
            return;
          $state.go('main');
        });
      }
    });
});
