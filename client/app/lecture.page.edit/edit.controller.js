angular
  .module('clientApp')
  .controller('editLectureController',
  /* @ngInject */
  function EditLectureController($scope, Lecture, rootUser, alert, $state, types, $stateParams) {
    function _init() {
      $scope.lecture = new Lecture();
      $scope.write = [[true, true, true, true], [false, false, true, true]];
      $scope.read = [[true, true, true, true], [true, true, true, true]];
      $scope.select = [];
      $scope.defaultGroup = 1;
    }

    $scope.newUserGroup = function () {
      $scope.lecture.userGroups.push($scope.userGroup);
      if ($scope.write.length < $scope.lecture.userGroups.length) {
        $scope.write.push([true, true, true, true]);
        $scope.read.push([true, true, true, true]);
      }
      $scope.userGroup = {};
    };

    $scope.newContentType = function () {
      $scope.lecture.contentTypes.push($scope.contentType);
      $scope.contentType = {};
      if ($scope.write[0].length < $scope.lecture.contentTypes.length) {
        $scope.write.forEach(function (write) {
          write.push(true);
        });
        $scope.read.forEach(function (read) {
          read.push(true);
        });
      }
    };


    $scope.cancel = cancel;
    $scope.save = save;
    $scope.rootUser = rootUser;
    $scope.majorTypes = types.majorTypes;
    $scope.registerPoilicyTypes = types.registerPoilicyTypes;
    _init();

    $scope.toggleContentTypes = function (index) {
      for (var i = 0; i < $scope.write.length; i++) {
        for (var j = 0; j < $scope.write[i].length; j++) {
          if (index === j) {
            $scope.write[i][j] = !$scope.write[i][j];
            $scope.read[i][j] = !$scope.read[i][j];
          }
        }
      }
    };

    $scope.toggleUserGroup = function (index) {
      for (var i = 0; i < $scope.write.length; i++) {
        for (var j = 0; j < $scope.write[i].length; j++) {
          if (index === i) {
            $scope.write[i][j] = !$scope.write[i][j];
            $scope.read[i][j] = !$scope.read[i][j];
          }
        }
      }
    };

    function save(lecture) {
      console.log(lecture);
      //var managerIds = [];
      //var query = {};
      //$scope.lecture.managers.forEach(function (manager) {
      //  managerIds.push(manager.id);
      //});
      //angular.copy(lecture, query);
      //query.managerIds = JSON.stringify(managerIds);
      //query.lessonString = JSON.stringify(query.lessons);
      //delete query.lessons;
      //lecture.save();
    }

    function cancel() {
      $state.go('lectures');
    }


    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id) {
        _init();
        return;
      }
      $scope.lecture = new Lecture(id);
    });

    //
    //function addLesson() {
    //  var newLesson = {};
    //  if ($scope.addType === 'one') {
    //    rangeCheck($scope.lesson.start, $scope.lesson.end);
    //    angular.copy($scope.lesson, newLesson);
    //    $scope.lecture.lessons.push(newLesson);
    //    $scope.showAddLesson = false;
    //    return;
    //  }
    //
    //  if (!$scope.lesson.start || !$scope.lesson.end || !$scope.lesson.timeStart || !$scope.lesson.timeEnd) {
    //    alert.error("시간을 입력해주세요.");
    //    return;
    //  }
    //  rangeCheck($scope.lesson.start, $scope.lesson.end);
    //  rangeCheck($scope.lesson.timeStart, $scope.lesson.timeEnd);
    //  var start = $scope.lesson.start, end = new Date($scope.lesson.end);
    //  end.setDate(end.getDate() + 1);
    //  for (var date = new Date(start); date < end; date.setDate(date.getDate() + 1)) {
    //    if (!$scope.week[date.getDay()])
    //      continue;
    //    var lessonStart = new Date(date);
    //    lessonStart.setHours($scope.lesson.timeStart.getHours());
    //    lessonStart.setMinutes($scope.lesson.timeStart.getMinutes());
    //    var lessonEnd = new Date(date);
    //    lessonEnd.setHours($scope.lesson.timeEnd.getHours());
    //    lessonEnd.setMinutes($scope.lesson.timeEnd.getMinutes());
    //    newLesson = {};
    //    angular.copy($scope.lesson, newLesson);
    //    newLesson.start = lessonStart;
    //    newLesson.end = lessonEnd;
    //    delete newLesson.timeStart;
    //    delete newLesson.timeEnd;
    //    $scope.lecture.lessons.push(newLesson);
    //  }
    //}
    //
    //function rangeCheck(start, end) {
    //  if (!start || !end)
    //    return;
    //  if (start > end) {
    //    var tmp = new Date(start);
    //    start.setTime(end.getTime());
    //    end.setTime(tmp.getTime());
    //  }
    //}

  });
