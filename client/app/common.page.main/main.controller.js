angular.module('clientApp')
  /* @ngInject */
  .controller('mainController', function (rootUser, Content, $state, $scope) {
    $scope.rootUser = rootUser;
    $scope.$watch(function () {
      return rootUser.id;
    }, function (id) {
      if (id !== undefined)
        return;
      $state.go('loginneed');
    });
    this.rootUser = rootUser;
    $scope.$parent.exist = this.rootUser.lectures.filter(function (lecture) {
        return lecture.sideMenu;
      }).length !== 0;
  });
