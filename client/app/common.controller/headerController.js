angular.module('clientApp')
  .controller('headerController',
  /* @ngInject */
  function ($scope, $state, $mdUtil, $mdSidenav, history, $location) {
    $scope.state = $state;
    $scope.toggleLeft = toggle('left');
    $scope.history = history;
    $scope.go = function (url) {
      $location.path(url);
    };

    function toggle(navID) {
      return $mdUtil.debounce(function () {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
          });
      }, 300);
    }
  });
