angular
  .module('clientApp')
  .controller('editLectureController',
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
        $scope.select = [];
        Lecture.findById(id).then(function (fromDB) {
          lecture = $scope.lecture = fromDB;
          lecture.userGroups.forEach(function (userGroup) {
            $scope.select.push(userGroup.defaultGroup);
          });
        });
      });


      function _init() {
        lecture = $scope.lecture = new Lecture();
        $scope.select = [false, true];
        $scope.userGroup = {};
        $scope.contentType = {};
      }

      $scope.newUserGroup = function () {
        lecture.userGroups.push($scope.userGroup);
        if (lecture.writable.length < lecture.userGroups.length) {
          lecture.writable.push(getTrueArray(lecture.contentTypes.length));
          lecture.readable.push(getTrueArray(lecture.contentTypes.length));
        }

        $scope.userGroup = {};

        function getTrueArray(length) {
          var result = [];
          for (var i = 0; i < length; i++)
            result.push(true);
          return result;
        }
      };

      $scope.newContentType = function () {
        lecture.contentTypes.push($scope.contentType);
        $scope.contentType = {};
        if (lecture.writable[0].length < lecture.contentTypes.length) {
          lecture.writable.forEach(function (writable) {
            writable.push(true);
          });
          lecture.readable.forEach(function (readable) {
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

      $scope.toggleContentTypes = function (index) {
        for (var i = 0; i < lecture.writable.length; i++) {
          for (var j = 0; j < lecture.writable[i].length; j++) {
            if (index === j) {
              lecture.writable[i][j] = !lecture.writable[i][j];
              lecture.readable[i][j] = !lecture.readable[i][j];
            }
          }
        }
      };

      $scope.toggleUserGroup = function (index) {
        for (var i = 0; i < lecture.writable.length; i++) {
          for (var j = 0; j < lecture.writable[i].length; j++) {
            if (index === i) {
              lecture.writable[i][j] = !lecture.writable[i][j];
              lecture.readable[i][j] = !lecture.readable[i][j];
            }
          }
        }
      };

      function save(lecture) {
        lecture.save().then(function (id) {
          $state.go('lecture', {id: id});
        });
      }

      function cancel() {
        $state.go('lectures');
      }


    });
