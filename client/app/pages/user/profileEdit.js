angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('profileEdit', {
      url: "/profile/:id/edit",
      templateUrl: "/pages/user/profileEdit.html",
      controller: function ($scope, user, $stateParams, Upload) {
        $scope.user = user;
        $scope.params = $stateParams;

        $scope.$watch('file', function (file) {
          if (!file)
            return;
          Upload.upload({
            url: '/api/v1/upload',
            data: {file: file}
          }).then(function (resp) {
            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
          }, function (resp) {
            console.log('Error status: ' + resp.status);
          }, function (evt) {
            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
          });
        });

      }
    });
});
