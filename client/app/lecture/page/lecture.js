angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture', {
      url: "/lecture/:id",
      templateUrl: "/lecture/page/lecture.html",
      controller: function ($scope, $stateParams, lectureBroker, user, alert, $state, types, $timeout) {

        $scope.types = types;
        $scope.user = user;
        $scope.remove = remove;
        $scope.enroll = enroll;
        $scope.isEnrolled = isEnrolled;
        $scope.setKey = setKey;

        function setKey(key) {
          if (!$scope.query)
            $scope.query = {};
          $scope.query.type = key;
          console.log(key);
        }

        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          lectureBroker.findById(id).then(function (lecture) {
            $scope.lecture = lecture;
            $scope.lecture.types = [{name: "강의자료"}, {name: "질문"}, {name: "과제", dueDate: true}];
            $scope.lecture.lessons.forEach(function (lesson) {
              lesson.title = lesson.name;
              lesson.start = new Date(lesson.start);
              lesson.end = new Date(lesson.end);
            });
          });
        });

        function remove(id) {
          if (!confirm("삭제하시겠습니까?"))
            return;
          lectureBroker.remove(id).then(function () {
            alert.info('폐강 되었습니다.');
            $state.go('lectures');
          })
        }

        function enroll(id) {
          lectureBroker.enroll(id).then(function () {
            alert.info('강의에 등록되었습니다.');
          });
        }

        function isEnrolled(id) {
          var lectures = user.lectures;
          if (!lectures)
            return false;
          for (var i = 0; i < lectures.length; i++)
            if (lectures[i].id === id)
              return true;
          return false;
        }

      }
    });
});
