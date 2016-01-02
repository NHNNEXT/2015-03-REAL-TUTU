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

        var likeIt = 'fa-heart';
        var notLikeIt = 'fa-heart-o';

        $timeout(function () {
          if ($scope.like === undefined)
            return;
          $scope.goodClass = $scope.like.ignoreTypeIncludes(rootUser.id) ? likeIt : notLikeIt;
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
              $scope.goodClass = likeIt;
              return;
            }
            if (response.code === responseCode.Like.REMOVE) {
              $scope.like.remove(rootUser.id);
              $scope.goodClass = notLikeIt;
            }
          });
        };
      }
    };
  });
