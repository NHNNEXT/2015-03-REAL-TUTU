'use strict';

/**
 * @ngdoc function
 * @name clientApp.controller:MypageCtrl
 * @description
 * # MypageCtrl
 * Controller of the clientApp
 */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('mypage.lecture', {
      url: "/lecture",
      controller: "MyPageLectureCtrl",
      templateUrl: "/views/mypage/lecture.html"
    })
    .state('mypage.homework', {
      url: "/homework",
      controller: "HomeworkCtrl",
      templateUrl: "/views/mypage/homework.html"
    })
    .state('mypage.modify', {
      url: "/modify",
      controller: "UsermodifyCtrl",
      templateUrl: "/views/mypage/modify.html"
    })
})
  .controller('MypageCtrl', function ($state) {
  });
