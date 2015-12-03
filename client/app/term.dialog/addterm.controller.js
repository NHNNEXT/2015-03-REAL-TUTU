/* @ngInject */
angular.module('clientApp').controller('addTermController', function ($scope, Term, dialog, responseCode, alert) {

  $scope.term = new Term();
  $scope.save = function () {
    $scope.term.save().then(function () {
      dialog.close();
    }, function (result) {
      if (result.code == responseCode.Term.ALREADY_EXIST)
        alert.warning("이미 존재하는 학기명입니다.");
    });
  }
});

