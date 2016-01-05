angular
  .module('clientApp')
  /* @ngInject */
  .controller('lectureDetailController', function ($scope, $stateParams, Lecture, rootUser, alert, $state, types, $timeout, http, $rootScope, hangul) {

    $scope.types = types;
    $scope.rootUser = rootUser;
    $scope.groupChange = groupChange;
    $scope.openIfRootUser = openIfRootUser;
    $scope.$state = $state;

    function openIfRootUser(o, e) {
      if (!$scope.lecture.hostUser.isRootUser())
        return;
      o(e);
    }

    $rootScope.$on('userStateChange', function () {
      getLecture($stateParams.id);
    });


    $scope.$watch(function () {
      return $stateParams.id;
    }, getLecture);

    function getLecture(id) {
      if (!id)
        return;
      Lecture.findById(id).then(function (fromDB) {
        $scope.lecture = fromDB;
        $state.current.header = fromDB.name;
        $scope.contents = fromDB.contents;
      });
    }

    function groupChange(group, user) {
      var query = {};
      query.groupId = group.id;
      query.userId = user.id;
      query.lectureId = $scope.lecture.id;
      http.put('/api/v1/lecture/userGroup', query).then(function (group) {
        user.group = group;
        alert.info(user.name + "님의 그룹이 " + hangul.getERO(group.name) + " 변경되었습니다");
      });
    }

  });
