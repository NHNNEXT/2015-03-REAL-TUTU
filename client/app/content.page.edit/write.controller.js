angular.module('clientApp').controller('contentWriteController',
  /* @ngInject */
  function ($stateParams, $scope, Content, types, rootUser, $state, Lecture, http, ContentGroup, User, alert) {
    $scope.rootUser = rootUser;
    $scope.addTimes = addTimes;

    $scope.toggleAll = function () {
      $scope.content.users.forEach(function (user) {
        user.submit = !user.submit;
      });
    };

    $scope.$watch(function () {
      return $stateParams.lectureId;
    }, function (id) {

      $scope.content = new Content();
      if (id === undefined)
        return;
      Lecture.getWriteInfoById($stateParams.lectureId).then(function (writeInfo) {
        $scope.contentGroups = [];
        writeInfo.contentGroups.forEach(function (contentGroup) {
          $scope.contentGroups.push(new ContentGroup(contentGroup));
        });
        $scope.content.users = [];
        $scope.hostUserId = writeInfo.hostUserId;
        writeInfo.users.forEach(function (user) {
          $scope.content.users.push(new User(user));
        });

        $scope.content.lectureId = writeInfo.id;
        $state.current.header = writeInfo.name;
      });
    });

    $scope.save = function (content) {
      if (content.extendWrite) {
        var result = [];
        if (!$scope.times) {
          alert.warning("기간을 입력해주세요.");
          return;
        }
        $scope.times.forEach(function (time) {
          var content = new Content($scope.content);
          content.startTime = time.startTime;
          content.endTime = time.endTime;
          result.push(content.getQuery());
        });
        var query = {};
        query.contents = result;
        query.lectureId = $stateParams.lectureId;
        http.post('/api/v1/content/list', query, true).then(function () {
          $state.go('lecture', {id: $stateParams.lectureId});
        });
        return;
      }

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
        newLesson.startTime = lessonStart;
        newLesson.endTime = lessonEnd;
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
