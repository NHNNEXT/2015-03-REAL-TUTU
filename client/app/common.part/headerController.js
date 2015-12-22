angular.module('clientApp')
  .controller('headerController',
  /* @ngInject */
  function ($timeout,$scope, $state,$rootScope, $mdUtil, $mdSidenav, rootUser) {
    $scope.state = $state;
    $scope.toggleLeft = toggle('left');
    $scope.rootUser = rootUser;
    $scope.rootScope = $rootScope;
    $scope.mode =$scope.rootScope.dynamicTheme;

    $timeout(function() {
      _init();
    },10);

    function _init() {
      var state = $scope.state;
      if(state.current.name ==='lectureEditor') {
        //$scope.$apply(function() {
        $rootScope.dynamicTheme = "editor";
        state.navtype ="editor";
        $rootScope.$broadcast('changeToEditorMode', {mode: "editor"});
        //});
      } else {
        console.log("else");
        if($rootScope.dynamicTheme === "editor"){
          //$scope.$apply(function() {
          $rootScope.dynamicTheme = "default";
          state.navtype ="default";
          $rootScope.$broadcast('changeToEditorMode', {mode: "default"});
          //});
        }
      }
    }

    $scope.$on('changeToEditorMode', function (event, args) {
      console.log("from header");
        $scope.mode = args.mode;
    });

    $rootScope.$on('$stateChangeStart',
      function(event, toState){
        console.log(toState);
        if(toState.name ==='lectureEditor') {
          //$scope.$apply(function() {
          $rootScope.dynamicTheme = "editor";
          $scope.navtype ="editor";
          $rootScope.$broadcast('changeToEditorMode', {mode: "editor"});
          //});
        } else {
          console.log("else");
          if($rootScope.dynamicTheme === "editor"){
            //$scope.$apply(function() {
            $rootScope.dynamicTheme = "default";
            $scope.navtype ="default";
            $rootScope.$broadcast('changeToEditorMode', {mode: "default"});
            //});
          }
        }
      });

    function toggle(navID) {
      return $mdUtil.debounce(function () {
        $mdSidenav(navID)
          .toggle()
          .then(function () {
          });
      }, 300);
    }
  });
