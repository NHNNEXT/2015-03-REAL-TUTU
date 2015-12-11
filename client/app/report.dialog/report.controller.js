angular.module('clientApp')
  /* @ngInject */
  .controller('reportController', function ($scope, rootUser, $state, http, alert, dialog) {
    $scope.rootUser = rootUser;
    $scope.$state = $state.current;
    $scope.send = function () {
      if (!$scope.body)
        return;
      var body = {};
      body.state = $state.current;
      body.body = $scope.body;
      http.post('/api/v1/report', {body: JSON.stringify(body)}).then(function(){
        alert.success("감사합니다.");
        dialog.close();
      });
    };
  });

