angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture.detail', {
      url: "/lecture/:id",
      templateUrl: "/pages/lecture/lecture.html",
      controller: function ($scope) {

        $scope.contents = [];

        $scope.contents.push(new Content(1, "제목", {id:1, name:"이름"}, 3, new Date(), new Date()));
        $scope.contents.push(new Content(1, "제목", {id:1, name:"이름"}, 3, new Date(), new Date()));
        $scope.contents.push(new Content(1, "제목", {id:1, name:"이름"}, 3, new Date(), new Date()));
        $scope.contents.push(new Content(1, "제목", {id:1, name:"이름"}, 3, new Date(), new Date()));
        $scope.contents.push(new Content(1, "제목", {id:1, name:"이름"}, 3, new Date(), new Date()));
        $scope.contents.push(new Content(1, "제목", {id:1, name:"이름"}, 3, new Date(), new Date()));

        function Content(id, title, writer, like, date, dueDate) {
          this.id = id;
          this.title = title;
          this.writer = writer;
          this.like = like;
          this.date = date;
          this.dueDate = dueDate;
        }
      }
    });
});
