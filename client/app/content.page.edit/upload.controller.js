angular.module('clientApp').controller('uploadController',
  /* @ngInject */
  function ($scope, alert, Upload, Attachment, $q, Auth) {

    $scope.Auth = Auth;

    var states = $scope.states = {};
    states.file = {};
    states.file.title = "첨부파일 업로드";
    states.file.upload = function (files) {
      if (!files)
        return;
      var promises = [];
      files.forEach(function (file) {
        file.progress = 0;
        $scope.uploading = true;
        promises.push(Upload.upload({
          url: '/api/v1/upload',
          data: {file: file}
        }).then(function (resp) {
          if (!$scope.$parent.content.attachments)
            $scope.$parent.content.attachments = [];
          $scope.$parent.content.attachments.push(new Attachment(resp.data.result));
        }, function () {
          alert.error(file.name + "업로드 실패 했습니다.");
        }, function (evt) {
          file.progress = parseInt(100.0 * evt.loaded / evt.total);
          var progress = 0;
          files.forEach(function (file) {
            progress += file.progress;
          });
          $scope.progress = progress / files.length;
        }));
      });
      $q.all(promises).then(function () {
        $scope.uploading = false;
        $scope.insert = false;
        $scope.file = undefined;
      });
    };

    states.img = {};
    states.img.title = "이미지 업로드";
    states.img.urlPlaceHolder = "삽입할 이미지의 URL을 입력해주세요.";
    states.img.insertBtn = "이미지 삽입";
    states.img.upload = function (file) {
      if (!["jpg", "jpeg", "gif", "png"].includes(file.name.split("\.").pop())) {
        alert.error("허용된 확장자가 아닙니다.");
        $scope.file = undefined;
        return;
      }
      $scope.uploading = true;
      Upload.upload({
        url: '/api/v1/upload',
        data: {file: file}
      }).then(function (resp) {
        $scope.uploading = false;
        $scope.insert = false;
        $scope.file = undefined;
        states.img.insertUrl(resp.data.result.downloadUrl, "이미지 형식을 확인해주세요.");
      }, function () {
        alert.error("업로드 실패 했습니다.");
        $scope.uploading = false;
      }, function (evt) {
        $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
      });
    };

    states.img.insertUrl = function (url, message) {
      isImage(url).then(function (test) {
        if (test) {
          $('[froala]').froalaEditor('image.insert', url);
          $scope.uploading = false;
          $scope.insert = false;
          $scope.file = undefined;
          return;
        }
        if (message) {
          alert.warning(message);
          return;
        }
        alert.warning('url을 확인해주세요');
      });
    };

    $scope.movie = {};
    states.mov = {};
    states.mov.title = "YOUTUBE 영상 업로드";
    states.mov.urlPlaceHolder = "삽입할 YOUTUBE 영상의 URL을 입력해주세요.";
    states.mov.insertBtn = "영상 삽입";
    states.mov.insertUrl = function (url) {
      var id = getYoutubeId(url);
      if (!id) {
        alert.warning('url을 확인해주세요');
        return;
      }
      insertYoutube(id);
    };

    $scope.uploadMovie = function () {
      var metadata = {
        snippet: {
          title: $scope.movie.title,
          description: $scope.movie.description
        },
        status: {
          privacyStatus: $scope.movie.status
        }
      };

      var file = $scope.movie.file;

      $.ajax({
        url: 'https://www.googleapis.com/upload/youtube/v3/videos?uploadType=resumable&part=status,snippet',
        method: 'POST',
        contentType: 'application/json',
        headers: {
          Authorization: 'Bearer ' + Auth.google.accessToken,
          'x-upload-content-length': file.size,
          'x-upload-content-type': file.type
        },
        data: JSON.stringify(metadata)
      }).done(function (data, textStatus, jqXHR) {
        $scope.uploading = true;
        resumableUpload({
          url: jqXHR.getResponseHeader('Location'),
          file: file,
          start: 0
        });
      });
    };


    function resumableUpload(options) {
      var ajax = $.ajax({
        url: options.url,
        method: 'PUT',
        contentType: options.file.type,
        headers: {
          'Content-Range': 'bytes ' + options.start + '-' + (options.file.size - 1) + '/' + options.file.size
        },
        xhr: function () {
          // Thanks to http://stackoverflow.com/a/8758614/385997
          var xhr = $.ajaxSettings.xhr();

          if (xhr.upload) {
            xhr.upload.addEventListener(
              'progress',
              function (e) {
                if (e.lengthComputable) {
                  var bytesTransferred = e.loaded;
                  var totalBytes = e.total;
                  $scope.progress = Math.round(100 * bytesTransferred / totalBytes);
                  if (!$scope.$$phase)
                    $scope.$apply();
                }
              },
              false
            );
          }

          return xhr;
        },
        processData: false,
        data: options.file
      });

      ajax.done(function (response) {
        var id = response.id;
        $scope.uploading = false;
        $scope.insert = false;
        $scope.file = undefined;
        if (!$scope.$$phase)
          $scope.$apply();
        insertYoutube(id);
      });

      ajax.fail(function () {
        alert.error("업로드 중 문제가 발생했습니다.");
      });
    }

    $scope.setState = function (name) {
      if ($scope.state && $scope.state.name === name) {
        $scope.insert = false;
        $scope.state = undefined;
        return;
      }
      $scope.insert = true;
      $scope.state = states[name];
      $scope.state.name = name;
      if (!$scope.state.urlPlaceHolder)
        $scope.uploadType = 'file';
      if (!$scope.uploadType)
        $scope.uploadType = 'file';
    };


    $scope.$watch('file', function (file) {
      if (!file)
        return;
      $scope.state.upload(file);
    });

    function isImage(src) {
      var deferred = $q.defer();

      var image = new Image();
      image.onerror = function () {
        deferred.resolve(false);
      };
      image.onload = function () {
        deferred.resolve(true);
      };
      image.src = src;

      return deferred.promise;
    }

    function getYoutubeId(url) {
      var p = /^(?:https?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com\/(?:embed\/|v\/|watch\?v=|watch\?.+&v=))((\w|-){11})(?:\S+)?$/;
      return (url.match(p)) ? RegExp.$1 : false;
    }

    function insertYoutube(id) {
      $('[froala]').froalaEditor('video.insert', "<iframe width='560' height='315' src='https://www.youtube.com/embed/" + id + "' frameborder='0' allowfullscreen></iframe>");
    }

  });
