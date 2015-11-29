angular.module('clientApp')
  .directive('submits', function () {
    return {
      restrict: 'E',
      scope: {
        submitdto: '='
      },
      templateUrl: '/submit.directive.submits/submits.html',
      /* @ngInject */
      controller: function (rootUser, $scope, Submit, User) {

        $scope.$watch('submitdto', function (submitdto) {
          console.log(submitdto)
          if (!submitdto)
            return;
          $scope.user = new User(submitdto.user);
          $scope.submits = [];
          $scope.submitId = submitdto.id;
          submitdto.submits.forEach(function (sumbit) {
            $scope.submits.push(new Submit(sumbit));
          })
        });

        $scope.submit = new Submit();
        $scope.rootUser = rootUser;
        $scope.writeSubmit = function (submit) {
          submit.submitId = $scope.submitId;
          submit.save().then(function (result) {
            submit.id = result.id;
            $scope.submits.push(submit);
            $scope.submit = new Submit();
          });
        };
      }
    };
  });
