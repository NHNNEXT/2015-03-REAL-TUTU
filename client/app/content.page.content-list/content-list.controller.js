angular.module('clientApp')
  /* @ngInject */
  .controller('contentListController', function ($scope, Content) {
    $scope.query = {};
    $scope.$watch('query', function(query){
      Content.getList(query).then(function(contents){
        $scope.contents = contents;
      });
    }, true);
  });
