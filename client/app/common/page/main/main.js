angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('main', {
      url: "/",
      templateUrl: "/common/page/main/main.html",
      headerClass:'navbar-fixed-top navbar-transparent',
      controller: function ($scope, user, contentBroker) {
        $scope.user = user;
        contentBroker.getList(function(list){
          $scope.contents = list;
        });


      }
    });
});
