angular.module('clientApp').config(function ($locationProvider) {
  $locationProvider.html5Mode({
    enabled: true,
    requireBase: false
  });
});


angular.module('clientApp').config(function ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise("/notfound");

  $stateProvider
    .state('main', {
      url: "/main",
      controller: "MainCtrl",
      templateUrl: "/views/main.html"
    })
    .state('login', {
      url: "/user/login",
      controller: "LoginCtrl",
      templateUrl: "/views/user/login.html"
    })
    .state('lecture', {
      url: "/lecture/:id",
      controller: "LectureCtrl",
      templateUrl: "/views/lecture.html"
    })
    .state('article', {
      url: "/article/:id",
      controller: "ArticleCtrl",
      templateUrl: "/views/article.html"
    })
    .state('board', {
      url: "/board/:id",
      controller: "BoardCtrl",
      templateUrl: "/views/board.html"
    })
    .state('articleEdit', {
      url: "/article/:id/edit",
      controller: "ArticleEditCtrl",
      templateUrl: "/views/articleEdit.html"
    })
    .state('homework', {
      url: "/homework/:id",
      controller: "HomeworkCtrl",
      templateUrl: "/views/homework.html"
    })
    .state('register', {
      url: "/user/register",
      controller: "RegisterCtrl",
      templateUrl: "/views/user/register.html"
    })
    .state('mypage', {
      url: "/mypage",
      redirectTo: 'mypage.lecture',
      controller: "MypageCtrl",
      templateUrl: "/views/mypage/mypage.html"
    })
    .state('notfound', {
      url: "/notfound",
      templateUrl: "/404.html"
    });

});
