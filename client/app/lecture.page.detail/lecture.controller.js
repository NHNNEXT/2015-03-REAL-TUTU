angular
  .module('clientApp')
  /* @ngInject */
  .controller('lectureDetailController', function ($scope, $stateParams, Lecture, rootUser, alert, $state, types) {

    $scope.types = types;
    $scope.rootUser = rootUser;
    $scope.isEnrolled = isEnrolled;
    $scope.setKey = setKey;


    function setKey(key) {
      if (!$scope.query)
        $scope.query = {};
      $scope.query.type = key;
    }

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      Lecture.findById(id).then(function (fromDB) {
        $scope.lecture = fromDB;
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
