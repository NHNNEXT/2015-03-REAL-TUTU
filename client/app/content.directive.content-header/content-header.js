angular.module('clientApp')
  .directive('contentHeader', function (Now) {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        index: '='
      },
      templateUrl: '/content.directive.content-header/content-header.html',
      link: function (s) {
        s.now = Now;
      }, controller: function (rootUser, $scope, $state, alert, $rootScope) {
        $scope.rootUser = rootUser;

        checkReadable();

        $rootScope.$on('userStateChange', checkReadable);

        function checkReadable() {
          $scope.readable = false;
          var lecture = rootUser.lectures.findById($scope.content.lectureId);
          if (lecture)
            $scope.readable = lecture.contentGroups.findById($scope.content.contentGroup.id) !== undefined;
        }

        $scope.ifReadableMove = function () {
          if (!$scope.readable) {
            alert.warning("열람 권한이 없습니다.");
            return;
          }
          $state.go('content', {id: $scope.content.id});
        };
      }
    };
  });
