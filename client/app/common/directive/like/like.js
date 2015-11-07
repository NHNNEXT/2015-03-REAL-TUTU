angular.module('clientApp')
  .directive('like', function () {
    return {
      restrict: 'E',
      scope: {
        type: '@',
        like: '=',
        target: '='
      },
      templateUrl: '/common/directive/like/like.html',
      controller: function ($scope, http, user, responseCode, $timeout) {


        $scope.getClass = function () {
          if ($scope.like.includes(user.id))
            return 'fa-heart';
          return 'fa-heart-o';
        };

        $timeout(function () {
          $scope.heartClass = $scope.like.ignoreTypeIncludes(user.id) ? 'fa-heart' : 'fa-heart-o';
        });

        if (!$scope.like)
          $scope.like = [];

        $scope.likeToggle = function () {
          if (!user.loginCheck())
            return;
          http.post('/api/v1/like', {
            id: $scope.target,
            type: responseCode.Like[$scope.type]
          }).then(function () {
          }, function (response) {
            if (response.code === responseCode.Like.ADD) {
              $scope.like.push(user.id);
              $scope.heartClass = 'fa-heart';
              return;
            }
            if (response.code === responseCode.Like.REMOVE) {
              $scope.like.remove(user.id);
              $scope.heartClass = 'fa-heart-o';
            }
          });
        }
      }
    };
  });
