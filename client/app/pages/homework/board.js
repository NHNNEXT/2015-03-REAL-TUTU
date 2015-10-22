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


        var homeworks = $scope.homeworks = [];
        homeworks.push(new Article("제목", new User(1, "image.png", "황")));
        homeworks.push(new Article("제목", new User(1, "image.png", "황")));
        homeworks.push(new Article("제목", new User(1, "image.png", "황")));
        homeworks.push(new Article("제목", new User(1, "image.png", "황")));

        function Article(title, writer) {
          this.id = 3;
          this.title = title;
          this.writer = writer;
        }

        function User(id, profile, name) {
          this.id = id;
          this.profile = profile;
          this.name = name;
        }


      }
    });
});
