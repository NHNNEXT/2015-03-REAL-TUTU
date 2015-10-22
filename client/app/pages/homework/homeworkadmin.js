angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('homeworkadmin', {
      url: "/homework/admin/:id",
      templateUrl: "/pages/homework/homeworkadmin.html",
      controller: function ($stateParams, $scope, http) {

        $scope.$watch(function () {
          return $stateParams.id;
        }, function () {
          http.post('/api/v1/homework', {id: $stateParams.id}, function () {

          });
        });


        var replies = $scope.replies = [];
        replies.push({writer: "하태호", date: new Date(2015, 10, 5), state: 0, body: "숙제잼"});
        replies.push({writer: "하태호", date: new Date(2015, 1, 5), state: 1, body: "숙제잼"});
        replies.push({writer: "하태호", date: new Date(2015, 12, 5), state: 2, body: "숙제잼"});
        replies.push({writer: "하태호", date: new Date(2015, 1, 5), state: 0, body: "숙제잼"});
        replies.push({writer: "하태호", date: new Date(2015, 1, 5), state: 0, body: "숙제잼"});

        var students = $scope.students = [];
        students.push({name: "하태호", state: 0});
        students.push({name: "황정민", state: 1});
        students.push({name: "박성호", state: 2});

        $scope.homework = {
          title: "신나는숙제",
          body: "숙제",
          dueDate: new Date(2015, 8, 10)
        };

        $scope.states = ["미확인", "미제출", "제출", "지연제출", "제출 확인 및 피드백 완료"];


        $scope.isTimeOut = function (reply) {
          return $scope.homework.dueDate < reply.date;
        };


      }
    });
});
