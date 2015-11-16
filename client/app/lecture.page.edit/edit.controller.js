angular
  .module('clientApp')
  .controller('editLectureController',
  /* @ngInject */
  function EditLectureController($scope, Lecture, rootUser, alert, $state, types, $stateParams) {

    var lecture;

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id) {
        _init();
        return;
      }
      $scope.select = [];
      Lecture.findById(id).then(function (fromDB) {
        lecture = $scope.lecture = fromDB;
        lecture.userGroups.forEach(function(userGroup){
          $scope.select.push(userGroup.defaultGroup)
        });
      });
    });


    function _init() {
      lecture = $scope.lecture = new Lecture();
      $scope.select = [false, true];
      $scope.userGroup = {};
      $scope.contentType = {};
    }

    $scope.newUserGroup = function () {
      lecture.userGroups.push($scope.userGroup);
      if (lecture.writable.length < lecture.userGroups.length) {
        lecture.writable.push(getTrueArray(lecture.contentTypes.length));
        lecture.readable.push(getTrueArray(lecture.contentTypes.length));
      }

      $scope.userGroup = {};

      function getTrueArray(length) {
        var result = [];
        for (var i = 0; i < length; i++)
          result.push(true);
        return result;
      }
    };

    $scope.newContentType = function () {
      lecture.contentTypes.push($scope.contentType);
      $scope.contentType = {};
      if (lecture.writable[0].length < lecture.contentTypes.length) {
        lecture.writable.forEach(function (writable) {
          writable.push(true);
        });
        lecture.readable.forEach(function (readable) {
          readable.push(true);
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
      for (var i = 0; i < lecture.writable.length; i++) {
        for (var j = 0; j < lecture.writable[i].length; j++) {
          if (index === j) {
            lecture.writable[i][j] = !lecture.writable[i][j];
            lecture.readable[i][j] = !lecture.readable[i][j];
          }
        }
      }
    };

    $scope.toggleUserGroup = function (index) {
      for (var i = 0; i < lecture.writable.length; i++) {
        for (var j = 0; j < lecture.writable[i].length; j++) {
          if (index === i) {
            lecture.writable[i][j] = !lecture.writable[i][j];
            lecture.readable[i][j] = !lecture.readable[i][j];
          }
        }
      }
    };

    function save(lecture) {
      lecture.save().then(function (id) {
        $state.go('lecture', {id: id});
      });
    }

    function cancel() {
      $state.go('lectures');
    }



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
