angular.module('clientApp')
  .controller('headerController',
  /* @ngInject */
  function ($scope, $state, $mdUtil, $mdSidenav) {
    $scope.state = $state;
    $scope.toggleLeft = toggle('left');

    function toggle(navID) {
      return $mdUtil.debounce(function () {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
          });
      }, 300);
    }
  });
