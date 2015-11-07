angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture', {
      url: "/lecture/:id",
      templateUrl: "/lecture/page/lecture.html",
      controller: function ($scope, $stateParams, lectureBroker, user, alert, $state) {

        $scope.user = user;
        $scope.remove = remove;
        $scope.enroll = enroll;
        $scope.isEnrolled = isEnrolled;

        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          lectureBroker.findById(id).then(function (lecture) {
            $scope.lecture = lecture;
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
