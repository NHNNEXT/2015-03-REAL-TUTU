angular.module('clientApp')
  .directive('submits', function () {
    return {
      restrict: 'E',
      scope: {
        submitdto: '=',
        attach: '='
      },
      templateUrl: '/submit.directive.submits/submits.html',
      /* @ngInject */
      controller: function (rootUser, $scope, Submit, User, emoticon) {

        $scope.$watch('submitdto', function (submitdto) {
          if (!submitdto)
            return;
          $scope.user = new User(submitdto.user);
          $scope.submits = submitdto.submits;
          $scope.submitId = submitdto.id;
          for (var i = 0; i < submitdto.submits.length; i++) {
            submitdto.submits[i] = new Submit(submitdto.submits[i]);
          }
        });

        $scope.submit = new Submit();
        $scope.rootUser = rootUser;
        $scope.writeSubmit = function (submit) {
          submit.submitId = $scope.submitId;
          submit.save().then(function (result) {
            $scope.submits.push(new Submit(result));
            if ($scope.user.id === result.id)
              emoticon.submitDone();
            $scope.submit = new Submit();

          });
        };
      }
    };
  });
