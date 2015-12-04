angular.module('clientApp')
  .controller('headerController',
  /* @ngInject */
  function ($scope, $state, $mdUtil, $mdSidenav, rootUser) {
    $scope.state = $state;
    $scope.toggleLeft = toggle('left');

    $scope.rootUser = rootUser;

    function toggle(navID) {
      return $mdUtil.debounce(function () {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
          });
      }, 300);
    }
  });
