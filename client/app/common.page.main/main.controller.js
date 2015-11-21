angular
  .module('clientApp')
  /* @ngInject */
  .controller('mainController', function ($scope, rootUser, Content) {
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
