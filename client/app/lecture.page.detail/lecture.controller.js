angular
  .module('clientApp')
  /* @ngInject */
  .controller('lectureDetailController', function ($scope, $stateParams, Lecture, rootUser, alert, $state, types) {

    $scope.types = types;
    $scope.rootUser = rootUser;
    $scope.isEnrolled = isEnrolled;
    $scope.setType = setType;


    function setType(type) {
      if (!type) {
        $scope.contents = $scope.lecture.contents;
        return;
      }
      $scope.contents = [];
      $scope.lecture.contents.forEach(function (content) {
        if (content.type.id === type.id)
          $scope.contents.push(content);
      });
    }

    var tabIndexes = {
      list: 0,
      timetable: 1,
      timeline: 2
    };

    $scope.$watch(function () {
      return $stateParams.tab;
    }, function (tab) {
      if (!tab)
        return;
      $scope.tabIndex = tabIndexes[tab];
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


    function isEnrolled(id) {
      var lectures = rootUser.lectures;
      if (!lectures)
        return false;
      for (var i = 0; i < lectures.length; i++)
        if (lectures[i].id === id)
          return true;
      return false;
    }

  });
