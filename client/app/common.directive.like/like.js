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
        $scope.getClass = function () {
          if ($scope.like.includes(rootUser.id))
            return 'fa-heart';
          return 'fa-heart-o';
        };

        $timeout(function () {
          if ($scope.like === undefined)
            return;
          $scope.heartClass = $scope.like.ignoreTypeIncludes(rootUser.id) ? 'fa-heart' : 'fa-heart-o';
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
              $scope.heartClass = 'fa-heart';
              return;
            }
            if (response.code === responseCode.Like.REMOVE) {
              $scope.like.remove(rootUser.id);
              $scope.heartClass = 'fa-heart-o';
            }
          });
        };
      }
    };
  });
