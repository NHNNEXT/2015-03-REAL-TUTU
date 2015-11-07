(function () {
  'use strict';
  angular.module('clientApp').config(function ($stateProvider) {
    $stateProvider
      .state('lectureEdit', {
        headerClass: 'navbar-fixed-top navbar-transparent',
        url: "/lecture/:id/edit",
        templateUrl: "/lecture/page/edit/edit.html",
        controller: EditLectureController
      })
      .state('lectureNew', {
        headerClass: 'navbar-fixed-top navbar-transparent',
        url: "/lecture/new",
        templateUrl: "/lecture/page/edit/edit.html",
        controller: EditLectureController
      });
  });


  angular
    .module('clientApp')
    .controller('EditLectureController', EditLectureController);

  /* @ngInject */
  function EditLectureController($scope, lectureBroker, user, alert, $state, types, $stateParams) {

    $scope.edit = edit;
    $scope.cancel = cancel;
    $scope.addLesson = addLesson;
    $scope.user = user;
    $scope.majorTypes = types.majorTypes;
    $scope.push = push;
    _init();

    function push(selected) {
      if (!selected || !selected.email)
        return;
      if (!$scope.lecture.managers.includes(selected)) {
        $scope.lecture.managers.push(selected);
      }
    }

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id)
        return;
      lectureBroker.findById(id).then(function (lecture) {
        $scope.lecture = lecture;
        if (!$scope.lecture.lessons)
          return;
        $scope.lecture.lessons.forEach(function (lesson) {
          lesson.start = new Date(lesson.start);
          lesson.end = new Date(lesson.end);
        })
      });
    });

    function _init() {
      $scope.week = [];
      $scope.lesson = {};

      $scope.lecture = {
        lessons: [],
        managers : []
      };
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

    function edit(lecture) {
      var managerIds = [];
      var query = {};
      $scope.lecture.managers.forEach(function (manager) {
        managerIds.push(manager.id)
      });
      angular.copy(lecture, query);
      query.managerIds = JSON.stringify(managerIds);
      query.lessonString = JSON.stringify(query.lessons);
      delete query.lessons;
      lectureBroker.edit(query).then(function (result) {
        $state.go('lecture', {id: result.id});
      });
    }

    function cancel() {
      $state.go('lectures');
    }
  }
}());
