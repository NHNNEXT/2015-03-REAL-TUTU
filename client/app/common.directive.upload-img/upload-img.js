angular.module('clientApp')
  .directive('uploadImg', function () {
    return {
      restrict: 'E',
      scope: {
        done: '=',
        ngModel: '=',
        default: '@'
      },
      templateUrl: "/common.directive.upload-img/upload-img.html",
      /* @ngInject */
      controller: function ($scope, Upload, alert) {
        $scope.progress = 0;
        $scope.$watch('file', function (file) {
          if (!file)
            return;
          $scope.uploading = true;
          Upload.upload({
            url: '/api/v1/upload',
            data: {file: file}
          }).then(function (resp) {
            $scope.uploading = false;
            if (typeof $scope.done === 'function')
              $scope.done(resp);
          }, function () {
            alert.error("업로드 실패 했습니다.");
            $scope.uploading = false;
          }, function (evt) {
            $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
          });
        });
      }
    };
  });
