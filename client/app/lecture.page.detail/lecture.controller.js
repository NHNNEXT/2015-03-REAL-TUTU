angular
  .module('clientApp')
  /* @ngInject */
  .controller('lectureDetailController', function ($scope, $stateParams, Lecture, rootUser, alert, $state, types, $timeout, http) {

    $scope.types = types;
    $scope.rootUser = rootUser;
    $scope.setType = setType;
    $scope.groupChange = groupChange;
    $scope.openIfRootUser = openIfRootUser;

    function openIfRootUser(o, e) {
      if (!$scope.lecture.hostUser.isRootUser())
        return;
      o(e);
    }


    function setType(contentGroup) {
      if (!contentGroup) {
        $scope.contents = $scope.lecture.contents;
        return;
      }
      $scope.contents = [];
      $scope.lecture.contents.forEach(function (content) {
        if (content.contentGroup.id === contentGroup.id)
          $scope.contents.push(content);
      });
    }

    var tabIndexes = {
      list: 0,
      timetable: 1,
      users: 2,
      request: 4
    };

    $scope.$watch(function () {
      return $stateParams.tab;
    }, function (tab) {
      if (!tab)
        return;
      $timeout(function () {
        $scope.tabIndex = tabIndexes[tab];
      }, 200);
    });


    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id)
        return;
      Lecture.findById(id).then(function (fromDB) {
        $scope.lecture = fromDB;
        $state.current.header = fromDB.name;
        $scope.contents = fromDB.contents;
        fromDB.contents.forEach(function (content) {
          content.startTime = new Date(content.startTime);
          content.endTime = new Date(content.endTime);
        });
      });
    });


    function groupChange(group, user) {
      var query = {};
      query.groupId = group.id;
      query.userId = user.id;
      query.lectureId = $scope.lecture.id;
      http.put('/api/v1/lecture/userGroup', query).then(function (group) {
        user.group = group;
      });
    }

  });
