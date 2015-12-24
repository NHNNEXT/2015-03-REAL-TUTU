/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {

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
    controller: 'contentWriteController'
  };
  states.content = {
    templateUrl: "/content.page.detail/detail.html",
    controller: "contentDetailController"
  };
  states.contentEdit = {
    templateUrl: "/content.page.edit/edit.html",
    controller: 'contentEditController'
  };
  states.lectureEdit = {
    templateUrl: "/lecture.page.edit/edit.html",
    controller: 'editLectureController',
    header: "강의 관리"
  };
  states.lectureNew = {
    templateUrl: "/lecture.page.edit/edit.html",
    controller: 'editLectureController',
    header: "강의 등록"
  };
  states.lecture = {
    templateUrl: "/lecture.page.detail/lecture.html",
    controller: 'lectureDetailController'
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

  states.main.url = "/";
  states.notfound.url = "/페이지를찾으려는노오오력이부족하다";
  states.loginneed.url = "/로그인하라고전해라아";
  states.content.url = "/게시물/:id";
  states.contentEdit.url = "/게시물/:id/수정";
  states.contentNew.url = "/게시물/:lectureId/쓰기";
  states.lectures.url = "/전체강의리스트";
  states.lecture.url = "/강의/:id?:tab";
  states.lectureNew.url = "/강의등록";
  states.lectureEdit.url = "/강의/:id/수정";
  states.messages.url = "/소식";
  states.profile.url = "/프로필/:id";
  states.mylectures.url = "/내강의";


  [
    "main",
    "notfound",
    "loginneed",
    "content",
    "contentEdit",
    "contentNew",
    "lectures",
    "lecture",
    "lectureNew",
    "lectureEdit",
    "messages",
    "profile",
    "mylectures"
  ].forEach(function (key) {
    $stateProvider.state(key, states[key]);
  });


});
