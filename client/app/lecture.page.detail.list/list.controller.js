angular.module('clientApp')
  /* @ngInject */
  .controller('contentListController', function ($scope, Content, $stateParams) {
    $scope.query = {contentGroupName: $stateParams.contentGroupName, lectureId: $stateParams.id};
  });
