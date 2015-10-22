angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('homeworkBoard', {
      url: "/homework/board/:id",
      templateUrl: "/pages/homework/board.html",
      controller: function ($stateParams, $scope, http) {

        $scope.$watch(function () {
          return $stateParams.id;
        }, function () {
          http.post('/api/v1/homework', {boardId: $stateParams.id}, function () {

          });
        });

        $scope.remainDate = function (date) {
          var now = new Date();
          return parseInt((date - now) / 1000 / 60 / 60 / 24 * 10) / 10;
        };


        var homeworks = $scope.homeworks = [];
        homeworks.push(new Article("자바지기와함께하는신나는숙제", "박재성", new Date(2015, 10, 31)));
        homeworks.push(new Article("자바지기와함께하는신나는숙제", "박재성", new Date(2015, 10, 31)));
        homeworks.push(new Article("자바지기와함께하는신나는숙제", "박재성", new Date(2015, 10, 31)));
        homeworks.push(new Article("자바지기와함께하는신나는숙제", "박재성", new Date(2015, 10, 31)));
        homeworks.push(new Article("자바지기와함께하는신나는숙제", "박재성", new Date(2015, 10, 31)));

        function Article(title, writer, dueDate) {
          this.id = 3;
          this.title = title;
          this.writer = writer;
          this.dueDate = dueDate;
        }

      }
    });
});
