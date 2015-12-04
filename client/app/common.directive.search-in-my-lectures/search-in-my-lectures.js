angular.module('clientApp')
  .directive('searchInMyLectures', function () {
    return {
      restrict: 'E',
      templateUrl: '/common.directive.search-in-my-lectures/search-in-my-lectures.html',
      bindToController: true,
      controllerAs: "ctrl",
      /* @ngInject */
      controller: function (http, $state) {
        this.querySearch = function (keyword) {
          if (!keyword)
            return;
          return http.get("/api/v1/search/mylectures", {keyword: keyword});
        };

        this.moveTo = function (content) {
          if (!content)
            return;
          $state.go('content', {id: content.id});
        };
      }
    };
  });
