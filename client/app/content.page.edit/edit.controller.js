angular.module('clientApp').controller('contentEditController',
  /* @ngInject */
  function ($stateParams, $scope, Content, types, rootUser, $state, Lecture, alert) {
    $scope.rootUser = rootUser;
    $scope.addTimes = addTimes;

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (id === undefined) {
        $scope.content = new Content();
        Lecture.findById($stateParams.lectureId).then(function (lecture) {
          $scope.content.contentTypes = lecture.contentTypes;
        });
        return;
      }
      Content.findById(id).then(function (content) {
        $scope.content = content;
      });
    });

    $scope.edit = function (content) {
      content.save().then(function (response) {
        var id = content.id === undefined ? response.id : content.id;
        $state.go('content', {id: id});
      });
    };


    $scope.range = {week: []};

    $scope.$watch('range', function () {
      addTimes();
    }, true);

    function addTimes() {
      if (!($scope.range.startDate && $scope.range.endDate && $scope.range.startTime && $scope.range.endTime)) {
        return;
      }
      rangeCheck($scope.range.startDate, $scope.range.endDate);
      rangeCheck($scope.range.startTime, $scope.range.endTime);
      $scope.times = [];
      var start = new Date($scope.range.startDate.getTime()), end = new Date($scope.range.endDate.getTime());
      end.setDate(end.getDate() + 1);
      for (var date = new Date(start); date < end; date.setDate(date.getDate() + 1)) {
        if (!$scope.range.week[date.getDay()])
          continue;
        var lessonStart = new Date(date.getTime());
        lessonStart.setHours($scope.range.startTime.getHours());
        lessonStart.setMinutes($scope.range.startTime.getMinutes());
        var lessonEnd = new Date(date.getTime());
        lessonEnd.setHours($scope.range.endTime.getHours());
        lessonEnd.setMinutes($scope.range.endTime.getMinutes());
        var newLesson = {};
        newLesson.start = lessonStart;
        newLesson.end = lessonEnd;
        $scope.times.push(newLesson);
      }
    }

    function rangeCheck(start, end) {
      if (!start || !end)
        return;
      if (start > end) {
        var tmp = new Date(start);
        start.setTime(end.getTime());
        end.setTime(tmp.getTime());
      }
    }


  });
