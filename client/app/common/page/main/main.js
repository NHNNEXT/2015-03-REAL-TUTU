angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('main', {
      url: "/",
      templateUrl: "/common/page/main/main.html",
      headerClass: 'navbar-fixed-top navbar-transparent',
      controller: function ($scope, user, contentBroker, types, $timeout) {
        $scope.user = user;
        $scope.types = types;

        var wall = new freewall('#freewall');
        $scope.$watch(function () {
          wall.fitWidth();
        });


        contentBroker.getList(function (list) {
          $scope.contents = list;
          list.forEach(function (content) {
            content.style = ranStyle(content);
          });
        });

        function ranStyle(content) {
          var style = {};
          style.width = ['200px', '300px', '400px'][ranInt(3)];
          style.height = ['200px', '300px', '250px'][ranInt(3)];
          style['background-color'] = ["#149c82", "#e74c3c", "#3498db"][content.type];
          return style;
        }

        function ranInt(int) {
          return parseInt(Math.random() * int);
        }


      }
    });
});
