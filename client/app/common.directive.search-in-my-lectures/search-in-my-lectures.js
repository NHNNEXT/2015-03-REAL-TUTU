angular.module('clientApp')
  /* @ngInject */
  .directive('searchInMyLectures', function ($mdSidenav) {
    return {
      restrict: 'E',
      templateUrl: '/common.directive.search-in-my-lectures/search-in-my-lectures.html',
      bindToController: true,
      controllerAs: "ctrl",
      /* @ngInject */
      controller: function ($scope, Content, $state) {

        $scope.status = "on";

        $scope.toggle = function () {
          $scope.$apply(function () {
            $scope.status = ($scope.status === 'on' ? 'off' : 'on');
          });
        };

        this.querySearch = function (keyword) {
          if (!keyword)
            return;
          return Content.getListInMyLectures({keyword: keyword});
        };
        this.moveTo = function (content) {
          if (!content)
            return;
          $mdSidenav('left').close();
          $state.go('content', {id: content.id});
        };
      }
    };
  });
