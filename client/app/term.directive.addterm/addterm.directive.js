angular.module('clientApp').directive('addTerm', function () {
  return {
    restrict: 'E',
    templateUrl: '/term.directive.addterm/addterm.html',
    /* @ngInject */
    controller: function ($scope, Term, responseCode, alert) {
      $scope.term = new Term();
      $scope.save = function () {
        $scope.term.save().then(function () {
          $scope.newTerm = false;
        }, function (result) {
          if (result.code === responseCode.Term.ALREADY_EXIST)
            alert.warning("이미 존재하는 학기명입니다.");
        });
      };
    }
  };
});

