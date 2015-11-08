angular.module('clientApp').config(function ($stateProvider) {
  var controller = function ($stateParams, $scope, contentBroker, types, user, $state) {
    $scope.user = user;
    $scope.content = {type: $stateParams.type, lectureId: $stateParams.lectureId};
    $scope.contentTypes = types.contentTypes;


    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      if (!id)
        return;
      contentBroker.findById(id).then(function (result) {
        angular.copy(result, $scope.content);
        $scope.content.dueDate = new Date($scope.content.dueDate);
        $scope.content.writeDate = new Date($scope.content.writeDate);
      });
    });

    function getLectureTypes(id) {
      console.log(user.lectures);
      if (!user.lectures)
        return;
      for (var i = 0; i < user.lectures.length; i++)
        if (user.lectures[i].id == id)
          return JSON.parse(user.lectures[i].types);
    }

    $scope.edit = function (content) {
      contentBroker.edit(content).then(function () {
        $state.go('lecture', {id: content.lectureId});
      });
    };

    $scope.$watch(function () {
      return user.lectures;
    }, function (lectures) {
      $scope.lectures = [];
      $scope.names = {};
      if (!lectures)
        return;
      lectures.forEach(function (lecture) {
        $scope.lectures.pushIfNotExist(lecture.id);
        $scope.names[lecture.id] = lecture.name;
      });
    });

    $scope.$watch(function () {
      return $stateParams.lectureId;
    }, function (id) {
      if (!$scope.content)
        $scope.content = {};
      $scope.content.lectureId = id;
    });

    $scope.$watch('content.lectureId', function (id) {
      console.log(id);
      $scope.types = getLectureTypes(id);
      if($scope.types == null){
        $scope.types = [{name: "강의자료"}, {name: "질문"}, {name: "과제", dueDate: true}];
      }
    });
  };
  $stateProvider
    .state('contentEdit', {
      url: "/content/:id/edit?:type?:lectureId",
      templateUrl: "/content/page/contentEdit.html",
      controller: controller
    })
    .state('contentNew', {
      url: "/content/write?:type?:lectureId",
      templateUrl: "/content/page/contentEdit.html",
      controller: controller
    });
});
