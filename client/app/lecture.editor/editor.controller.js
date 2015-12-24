angular
  .module('clientApp')
  .controller('editorLectureController',
    /* @ngInject */
    function EditLectureController($scope, Lecture, rootUser, alert, $state, types, $stateParams) {

      var lecture;

      $scope.$watch(function () {
        return $stateParams.id;
      }, function (id) {
        if (!id) {
          _init();
          return;
        }
        Lecture.findById(id).then(function (fromDB) {
          lecture = $scope.lecture = fromDB;
        });
      });

      function _init() {
        lecture = $scope.lecture = new Lecture();
        $scope.userGroup = {};
        $scope.contentGroup = {contentType: "GENERAL"};
      }

      $scope.newUserGroup = function () {
        lecture.userGroups.push($scope.userGroup);
        if (lecture.writable.length < lecture.userGroups.length) {
          lecture.writable.push(getTrueArray(lecture.contentGroups.length));
          lecture.readable.push(getTrueArray(lecture.contentGroups.length));
          lecture.submitReadable.push(getTrueArray(lecture.contentGroups.length));
        }

        $scope.userGroup = {};

        function getTrueArray(length) {
          var result = [];
          for (var i = 0; i < length; i++)
            result.push(true);
          return result;
        }
      };

      $scope.newContentGroup = function () {
        lecture.contentGroups.push($scope.contentGroup);
        $scope.contentGroup = {contentType: "GENERAL"};
        if (lecture.writable[0].length < lecture.contentGroups.length) {
          lecture.writable.forEach(function (writable) {
            writable.push(true);
          });
          lecture.readable.forEach(function (readable) {
            readable.push(true);
          });
          lecture.submitReadable.forEach(function (readable) {
            readable.push(true);
          });
        }
      };

      $scope.cancel = cancel;
      $scope.save = save;
      $scope.rootUser = rootUser;
      $scope.majorTypes = types.majorTypes;
      $scope.registerPoilicyTypes = types.registerPoilicyTypes;
      _init();

      $scope.toggleContentGroups = function (index) {
        for (var i = 0; i < lecture.writable.length; i++) {
          for (var j = 0; j < lecture.writable[i].length; j++) {
            if (index === j) {
              lecture.writable[i][j] = !lecture.writable[i][j];
              lecture.readable[i][j] = !lecture.readable[i][j];
              lecture.submitReadable[i][j] = !lecture.submitReadable[i][j];
            }
          }
        }
      };

      $scope.toggleUserGroups = function (index) {
        for (var i = 0; i < lecture.writable.length; i++) {
          for (var j = 0; j < lecture.writable[i].length; j++) {
            if (index === i) {
              lecture.writable[i][j] = !lecture.writable[i][j];
              lecture.readable[i][j] = !lecture.readable[i][j];
              lecture.submitReadable[i][j] = !lecture.submitReadable[i][j];
            }
          }
        }
      };

      function save(lecture) {
        lecture.save().then(function (id) {
          if (!lecture.id) {
            lecture.id = id;
            lecture.sideMenu = true;
            rootUser.lectures.push(lecture);
            rootUser.hostingLectures.push(lecture);
          }
          $state.go('lecture', {id: id});
        });
      }

      function cancel() {
        $state.go('lectures');
      }


    });
