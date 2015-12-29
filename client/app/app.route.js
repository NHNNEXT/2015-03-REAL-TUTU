/* @ngInject */
angular.module('clientApp').config(function ($stateProvider, $urlRouterProvider) {

  var states = {};
  states.loginneed = {
    templateUrl: "/common.page.loginneed/loginneed.html",
    header: "로그인 해야합니다.",
    controller: 'loginNeedController'
  };
  states.main = {
    templateUrl: "/common.page.main/main.html",
    controller: 'mainController',
    controllerAs: 'main',
    header: "내 강의 일정"
  };
  states.notfound = {
    templateUrl: "/common.page.notfound/notfound.html",
    header: "404 NOT FOUND"
  };
  states.contentNew = {
    templateUrl: "/content.page.edit/write.html",
    controller: 'contentWriteController',
    confirm: true
  };
  states.content = {
    templateUrl: "/content.page.detail/detail.html",
    controller: "contentDetailController"
  };
  states.contentEdit = {
    templateUrl: "/content.page.edit/edit.html",
    controller: 'contentEditController',
    confirm: true
  };
  states.lectureEdit = {
    templateUrl: "/lecture.page.edit/edit.html",
    controller: 'editLectureController',
    header: "강의 관리",
    confirm: true
  };
  states.lectureNew = {
    templateUrl: "/lecture.page.edit/edit.html",
    controller: 'editLectureController',
    header: "강의 등록",
    confirm: true
  };
  states.lecture = {
    templateUrl: "/lecture.page.detail/lecture.html",
    controller: 'lectureDetailController',
    redirectTo: 'lecture.list'
  };
  states.lectures = {
    header: "전체 강의 목록",
    templateUrl: "/lecture.page.list/list.html",
    controller: "LectureController",
    controllerAS: "lecture"
  };
  states.mylectures = {
    header: "내 강의",
    templateUrl: "/lecture.page.mylist/mylist.html",
    controller: "myListController"
  };
  states.messages = {
    header: "소식",
    controller: 'messagesController',
    templateUrl: "/message.page.list/messages.html"
  };
  states.profile = {
    controller: 'profileController',
    templateUrl: "/user.page.profile/profile.html"
  };

  states["lecture.people"] = {
    templateUrl: "/lecture.page.detail/lecture.people.html"
  };
  states["lecture.people"].url = "/참여자";

  states["lecture.list"] = {
    controller: 'contentListController',
    templateUrl: "/lecture.page.detail.list/list.html"
  };
  states["lecture.list"].url = "/:contentGroupName";


  states.main.url = "/";
  states.notfound.url = "/페이지없다";
  states.loginneed.url = "/로그인하라고전해라";
  states.content.url = "/게시물/:id";
  states.contentEdit.url = "/게시물/:id/수정";
  states.contentNew.url = "/게시물/:lectureId/쓰기";
  states.lectures.url = "/전체강의리스트";
  states.lecture.url = "/강의/:id?:tab";
  states.lectureNew.url = "/강의등록";
  states.lectureEdit.url = "/강의/:id/수정";
  states.messages.url = "/소식";
  states.profile.url = "/사용자정보/:email";
  states.mylectures.url = "/내강의";


  Object.keys(states).forEach(function (key) {
    $stateProvider.state(key, states[key]);
  });

  $urlRouterProvider.otherwise("/페이지없다");
  $urlRouterProvider.when('/강의/{id}', '/강의/{id}/1');


});
