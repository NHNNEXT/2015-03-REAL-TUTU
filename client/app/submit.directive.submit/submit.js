angular.module('clientApp')
  .directive('submit', function () {
    return {
      restrict: 'A',
      scope: {
        submit: '=',
        submits: '=',
        submitRequiredUser:'='
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
          confirm("삭제하시겠습니까?", undefined, function () {
            $scope.submit.remove().then(function () {
              $scope.submits.remove($scope.submit);
            });
          });
        };
      }
    };
  });
