app.config(["$locationProvider", function ($locationProvider) {
  $locationProvider.html5Mode({
    enabled: true,
    requireBase: false
  });
}]);

app.config(function ($stateProvider, $urlRouterProvider) {

  $urlRouterProvider.otherwise("/main");

  $stateProvider
    .state('main', {
      url: "/main/as",
      controller: "main",
      templateUrl: "/views/main.html"
    });

});
