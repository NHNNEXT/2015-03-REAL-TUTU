angular.module('clientApp')
  .directive('like', function () {
    return {
      restrict: 'E',
      scope: {
        type: '@',
        like: '=',
        target: '='
      },
      templateUrl: '/common.directive.like/like.html',
      /* @ngInject */
      controller: function ($scope, http, rootUser, responseCode, $timeout) {

        $timeout(function () {
          if ($scope.like === undefined)
            return;
          $scope.goodClass = $scope.like.ignoreTypeIncludes(rootUser.id) ? 'green' : '';
        });

        if (!$scope.like)
          $scope.like = [];

        $scope.likeToggle = function () {
          http.post('/api/v1/like', {
            id: $scope.target,
            type: responseCode.Like[$scope.type]
          }).then(function () {
          }, function (response) {
            if (response.code === responseCode.Like.ADD) {
              $scope.like.push(rootUser.id);
              $scope.goodClass = 'green';
              return;
            }
            if (response.code === responseCode.Like.REMOVE) {
              $scope.like.remove(rootUser.id);
              $scope.goodClass = '';
            }
          });
        };
      }
    };
  });
