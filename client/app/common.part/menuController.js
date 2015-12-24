angular.module('clientApp')
  .controller('menuController',
    /* @ngInject */
    function ($scope,$rootScope, rootUser,$mdTheming, $mdSidenav, $state,mainButler,Message) {


      $scope.state = $state;
      $scope.rootUser = rootUser;
      $scope.Message = Message;
      $scope.rootScope = $rootScope;
      $scope.mode =$scope.rootScope.dynamicTheme;

      $scope.reload = function() {
        $state.reload($state.current.name);
      };

      $scope.$on('changeToEditorMode', function (event, args) {
          $scope.mode = args.mode;
      });

      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
