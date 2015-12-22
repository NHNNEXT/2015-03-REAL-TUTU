angular.module('clientApp')
  .controller('menuController',
    /* @ngInject */
    function ($scope,$rootScope, rootUser,$mdTheming, $mdSidenav, $state,mainButler,Message) {


      $scope.state = $state;
      $scope.rootUser = rootUser;
      $scope.Message = Message;
      $scope.contentGroups = {};
      $scope.rootScope = $rootScope;
      $scope.mode =$scope.rootScope.dynamicTheme;

      $scope.reload = function() {
        $state.reload($state.current.name);
      };

      $scope.$on('changeToEditorMode', function (event, args) {
          $scope.mode = args.mode;
      });

      $scope.$watch('contents', function (contents) {
        if (!contents)
          return;
        contents.forEach(function (content) {
          $scope.contentGroups[content.contentGroup.name] = content.contentGroup;
          content.contentGroup.select = true;
        });
      });
      $scope.close = function () {
        $mdSidenav('left').close();
      };
    });
