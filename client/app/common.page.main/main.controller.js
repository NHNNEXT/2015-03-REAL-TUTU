(function () {

  angular
    .module('clientApp')
    /* @ngInject */
    .controller('mainController', function (rootUser, Content, $state, $scope) {

      $scope.$watch(function () {
        return rootUser.id;
      }, function (id) {
        if (id !== undefined)
          return;
        $state.go('loginneed');
      });

      var start = new Date();
      var end = new Date();
      start.setDate(start.getDate() - 7);
      end.setDate(end.getDate() + 3);
      $scope.start = start;
      $scope.end = end;

      this.rootUser = rootUser;
    });
})();
