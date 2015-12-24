angular.module('clientApp')
  /* @ngInject */
  .controller('contentListController', function ($scope, Content, $stateParams) {
    $scope.query = {contentGroupId: $stateParams.contentGroupId, lectureId:$stateParams.lectureId};

    $scope.page = 0;

    $scope.$watch('page', function (page) {
      if (!page)
        return;
      getContents();
    });

    $scope.$watch('query', function (query) {
      if (!query)
        return;
      getContents();

      Content.getListSize(query).then(function (size) {
        $scope.size = size;
        definePages();
      });
    }, true);

    var pageSize = 10;

    $scope.setPage = function (page) {
      $scope.page = page;
    };

    function definePages() {
      $scope.pages = [];
      for (var i = 0; i < $scope.size; i += pageSize) {
        $scope.pages.push(i);
      }
    }

    function getContents() {
      var q = {};
      angular.copy($scope.query, q);
      q.page = $scope.page;
      Content.getList(q).then(function (contents) {
        $scope.contents = contents;
      });
    }


  });
