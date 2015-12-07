angular.module('clientApp')
  .directive('submit', function () {
    return {
      restrict: 'A',
      scope: {
        submit: '=',
        submits: '=',
        attach:'='
      },
      templateUrl: '/submit.directive.submit/submit.html',
      /* @ngInject */
      controller: function (rootUser, $scope, confirm) {
        $scope.rootUser = rootUser;
        $scope.modify = function () {
          if ($scope.mod) {
            $scope.submit.save().then(function () {
              $scope.mod = false;
            });
            return;
          }
          $scope.mod = true;
        };
        $scope.delete = function () {
          if (!confirm("삭제하시겠습니까?"))
            return;
          $scope.submit.remove().then(function () {
            $scope.submits.remove($scope.submit);
          });
        };
      }
    };
  });
