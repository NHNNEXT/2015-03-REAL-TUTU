'use strict';

angular.module('clientApp')
  .directive('uploadImg', function () {
    return {
      restrict: 'E',
      scope: {
        data: '=',
        attr: '@'
      },
      templateUrl: "/common/directive/upload-img/upload-img.html",
      controller: function ($scope, Upload, alert) {
        $scope.getPath = function () {
          if (!$scope.data[$scope.attr])
            return '/resource/profile.png';
          return '/uploads/' + $scope.data[$scope.attr];
        };

        $scope.progress = 0;
        $scope.$watch('file', function (file) {
          if (!file)
            return;
          $scope.uploading = true;
          console.log(file);
          Upload.upload({
            url: '/api/v1/upload',
            data: {file: file}
          }).then(function (resp) {
            console.log(resp);
            $scope.uploading = false;
            $scope.data[$scope.attr] = resp.data.result;
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
