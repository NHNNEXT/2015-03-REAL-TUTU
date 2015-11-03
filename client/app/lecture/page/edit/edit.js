(function () {
  'use strict';
  angular.module('clientApp').config(function ($stateProvider) {
    $stateProvider
      .state('lectureEdit', {
        url: "/lecture/:id/edit",
        templateUrl: "/lecture/page/edit/edit.html",
        controller: EditLectureController
      })
      .state('lectureNew', {
        url: "/lecture/new",
        templateUrl: "/lecture/page/edit/edit.html",
        controller: EditLectureController
      });
  });


  angular
    .module('clientApp')
    .controller('EditLectureController', EditLectureController);

  /* @ngInject */
  function EditLectureController($scope, lectureBroker, user, alert, $state) {
    $scope.create = create;
    $scope.cancel = cancel;
    _init();
    $scope.addLesson = addLesson;
    $scope.user = user;
    $scope.majorTypes = ["전공필수", "전공선택", "교양"];

    $scope.push = function (selected) {
      if (!selected || !selected.email)
        return;
      if (!$scope.managers.includes(selected)) {
        $scope.managers.push(selected);
      }
    };

    function _init() {
      $scope.managers = [];
      $scope.week = [];
      $scope.lesson = {};

      $scope.lecture = {
        lessons: [],
        name: "",
        term: "",
        type: "",
        score: "",
        day: "",
        time: "",
        body: ""
      };
    }

    function addLesson() {
      var newLesson = {};
      if ($scope.addType === 'one') {
        rangeCheck($scope.lesson.start, $scope.lesson.end);
        angular.copy($scope.lesson, newLesson)
        $scope.lecture.lessons.push(newLesson);
        $scope.showAddLesson = false;
        return;
      }

      /*



       형 어제 파일 업로드 못하고 있던거 지금 찾아보니 서블릿 버전이 바뀌어서 그렇대요
       파일 업로드 기능 어떻게든 오늘내로는 완료시킬께요

       - 태호 -




       */
      if (!$scope.lesson.start || !$scope.lesson.end || !$scope.lesson.timeStart || !$scope.lesson.timeEnd) {
        alert("시간을 입력해주세요.");
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


    function create(lecture) {
      var managerIds = [];
      var query = {};
      $scope.managers.forEach(function (manager) {
        managerIds.push(manager.id)
      });
      lecture.managerIds = managerIds;
      angular.copy(lecture, query);
      query.lessonString = JSON.stringify(query.lessons);
      delete query.lessons;
      lectureBroker.create(query);
    }

    function cancel() {
      $state.go('lectures')
    }
  }
}());
