angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('lecture', {
      url: "/lecture/:id",
      templateUrl: "/lecture/page/lecture.html",
      controller: function ($scope, $stateParams, lectureBroker, user) {

        $scope.enroll = function (id) {
          lectureBroker.enroll(id, function (result) {

          });
        };

        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          lectureBroker.findById(id, function (lecture) {
            $scope.lecture = lecture;
          });
        });

        $scope.isEnrolled = function (id) {
          var lectures = user.lectures;
          if (!lectures)
            return false;
          for (var i = 0; i < lectures.length; i++)
            if (lectures[i].id === id)
              return true;
          return false;
        };

      }
    });
});
