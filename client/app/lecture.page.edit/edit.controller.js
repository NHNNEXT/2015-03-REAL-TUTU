angular
  .module('clientApp')
  .controller('editLectureController',
  /* @ngInject */
  function EditLectureController($scope, Lecture, rootUser, alert, $state, types, $stateParams) {

    $scope.cancel = cancel;
    $scope.addLesson = addLesson;

    $scope.save = save;
    $scope.rootUser = rootUser;
    $scope.majorTypes = types.majorTypes;
    $scope.push = push;
    $scope.newType = newType;
    _init();

    function push(selected) {
      if (!selected || !selected.email)
        return;
      if (!$scope.lecture.managers.includes(selected)) {
        $scope.lecture.managers.push(selected);
      }
    }

    function newType() {
      if (!$scope.lecture.types)
        $scope.lecture.types = [];
      $scope.lecture.types.push($scope.type);
      $scope.type = {};
    }

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id) {
        var lecture = $scope.lecture = new Lecture();
        lecture.types = [{name: "강의자료"}, {name: "질문"}, {name: "과제", dueDate: true}];
        lecture.lessons = [];
        lecture.managers = [];
        return;
      }
     $scope.lecture = new Lecture(id);
    });

    function _init() {
      $scope.week = [];
      $scope.lesson = {};
    }

    function addLesson() {
      var newLesson = {};
      if ($scope.addType === 'one') {
        rangeCheck($scope.lesson.start, $scope.lesson.end);
        angular.copy($scope.lesson, newLesson);
        $scope.lecture.lessons.push(newLesson);
        $scope.showAddLesson = false;
        return;
      }

      if (!$scope.lesson.start || !$scope.lesson.end || !$scope.lesson.timeStart || !$scope.lesson.timeEnd) {
        alert.error("시간을 입력해주세요.");
        return;
      }
      rangeCheck($scope.lesson.start, $scope.lesson.end);
      rangeCheck($scope.lesson.timeStart, $scope.lesson.timeEnd);
      var start = $scope.lesson.start, end = new Date($scope.lesson.end);
      end.setDate(end.getDate() + 1);
      for (var date = new Date(start); date < end; date.setDate(date.getDate() + 1)) {
        if (!$scope.week[date.getDay()])
          continue;
        var lessonStart = new Date(date);
        lessonStart.setHours($scope.lesson.timeStart.getHours());
        lessonStart.setMinutes($scope.lesson.timeStart.getMinutes());
        var lessonEnd = new Date(date);
        lessonEnd.setHours($scope.lesson.timeEnd.getHours());
        lessonEnd.setMinutes($scope.lesson.timeEnd.getMinutes());
        newLesson = {};
        angular.copy($scope.lesson, newLesson);
        newLesson.start = lessonStart;
        newLesson.end = lessonEnd;
        delete newLesson.timeStart;
        delete newLesson.timeEnd;
        $scope.lecture.lessons.push(newLesson);
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

    function save(lecture) {
      var managerIds = [];
      var query = {};
      $scope.lecture.managers.forEach(function (manager) {
        managerIds.push(manager.id);
      });
      angular.copy(lecture, query);
      query.managerIds = JSON.stringify(managerIds);
      query.lessonString = JSON.stringify(query.lessons);
      delete query.lessons;
      lecture.save();
    }

    function cancel() {
      $state.go('lectures');
    }
  });
