angular
  .module('clientApp')
  /* @ngInject */
  .controller('lectureDetailController', function ($scope, $stateParams, Lecture, rootUser, alert, $state, types) {

    $scope.types = types;
    $scope.rootUser = rootUser;
    $scope.isEnrolled = isEnrolled;
    $scope.setKey = setKey;
    $scope.toWritePage = toWritePage;

    function toWritePage() {
      rootUser.currentLecture = $scope.lecture;
      $state.go('contentNew');
    }

    function setKey(key) {
      if (!$scope.query)
        $scope.query = {};
      $scope.query.type = key;
    }

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      $scope.lecture = new Lecture(id);
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
