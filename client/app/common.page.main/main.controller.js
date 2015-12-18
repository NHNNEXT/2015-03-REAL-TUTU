(function () {

  angular
    .module('clientApp')
    /* @ngInject */
    .controller('mainController', function (rootUser, Content, $state, $scope, mainButler) {

      $scope.date = mainButler.date;
      $scope.$watch(function () {
        return rootUser.id;
      }, function (id) {
        if (id !== undefined)
          return;
        $state.go('loginneed');
      });

      this.rootUser = rootUser;

    });
})();
