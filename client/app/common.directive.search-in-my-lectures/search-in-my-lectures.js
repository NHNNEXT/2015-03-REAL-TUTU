angular.module('clientApp')
  /* @ngInject */
  .directive('searchInMyLectures', function ($mdSidenav) {
    return {
      restrict: 'E',
      templateUrl: '/common.directive.search-in-my-lectures/search-in-my-lectures.html',
      bindToController: true,
      controllerAs: "ctrl",
      /* @ngInject */
      controller: function ($scope,http, $state) {

        $scope.status="on";

        $scope.toggle = function() {
          $scope.$apply(function() {
            $scope.status = ($scope.status === 'on' ? 'off' : 'on');
          });
        };

        var self = this;
        this.querySearch = function (keyword) {
          if (!keyword)
            return;
          return http.get("/api/v1/search/mylectures", {keyword: keyword});
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
