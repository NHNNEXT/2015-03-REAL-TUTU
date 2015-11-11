angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profile', {
      url: "/profile/:id",
      templateUrl: "/user/page/profile.html",
      controller: function ($scope, user, $stateParams, userBroker, alert) {

        $scope.uploadCallback = uploadCallback;
        $scope.update = update;
        $scope.isRootUser = isRootUser;

        function uploadCallback(resp) {
          $scope.user.profileUrl = "/uploads/" + resp.data.result;
          update();
        }

        function update() {
          userBroker.update($scope.user).then(function (result) {
            alert.success("회원 정보가 수정되었습니다.");
            angular.merge(user, result);
            //바인딩이 끊어져서 할수없이 강제 입력;
            angular.element($('my-profile')).scope().user = user;
          });
        }

        function isRootUser() {
          if (!user.isLogged())
            return;
          return user.id === $scope.user.id;
        }

        $scope.user = {};
        $scope.$watch(function () {
          return $stateParams.id;
        }, function (id) {
          if (!id)
            return;
          userBroker.findById(id).then(function (result) {
            angular.copy(result, $scope.user);
          })
        });
      }
    });
});
