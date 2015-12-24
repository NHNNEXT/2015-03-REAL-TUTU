angular.module('clientApp').controller('loginNeedController',
  /* @ngInject */
  function ($scope, rootUser, $state) {
    $scope.$watch(function () {
      return rootUser.id;
    }, function (id) {
      if (id === undefined)
        return;
      $state.go('main');
    });
  });


