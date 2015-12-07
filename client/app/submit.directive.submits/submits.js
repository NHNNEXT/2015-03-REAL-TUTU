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
      controller: function (rootUser, $scope, Submit, User) {

        $scope.$watch('submitdto', function (submitdto) {
          if (!submitdto)
            return;
          $scope.user = new User(submitdto.user);
          $scope.submits = [];
          $scope.submitId = submitdto.id;
          submitdto.submits.forEach(function (sumbit) {
            $scope.submits.push(new Submit(sumbit));
          });
        });

        $scope.submit = new Submit();
        $scope.rootUser = rootUser;
        $scope.writeSubmit = function (submit) {
          submit.submitId = $scope.submitId;
          submit.save().then(function (result) {
            $scope.submits.push(new Submit(result));
            $scope.submit = new Submit();
          });
        };
      }
    };
  });
