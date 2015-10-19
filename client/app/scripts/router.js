angular.module('clientApp').config(["$locationProvider", function ($locationProvider) {
  $locationProvider.html5Mode({
    enabled: true,
    requireBase: false
  });
}]);

angular.module('clientApp').config(function ($stateProvider, $urlRouterProvider) {
  $urlRouterProvider.otherwise("/notfound");

  $stateProvider
    .state('main', {
      url: "/main",
      controller: "MainCtrl",
      templateUrl: "/views/main.html"
    })
    .state('mypage', {
      url: "/mypage",
      controller: "HeaderCtrl",
      templateUrl: "/views/mypage.html"
    })
    .state('notfound', {
      url: "/notfound",
      templateUrl: "/404.html"
    });

});
