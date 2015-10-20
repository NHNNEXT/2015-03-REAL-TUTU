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
