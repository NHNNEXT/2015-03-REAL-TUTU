angular
  .module('clientApp')
  /* @ngInject */
  .controller('mainController', function ($scope, rootUser, Content, $state) {

    $scope.tags = [];

    $scope.addTag = function (tag) {
      if (tag === undefined || tag.tag === undefined)
        return;
      $scope.tags.pushIfNotExist(tag.tag);
    };

    $scope.$watch(function () {
      return rootUser.id;
    }, function (id) {
      if (id !== undefined)
        return;
      $state.go('loginneed');
    });
    $scope.rootUser = rootUser;
    var classes = ['gray', 'green', 'yellow', 'blue', 'purple', 'red'];
    Content.getList().then(function (list) {
      $scope.contents = list;
      list.forEach(function (content, i) {
        content.row = 2;
        content.col = 2;
        content.class = classes[i % classes.length];
      });
    });


  });
