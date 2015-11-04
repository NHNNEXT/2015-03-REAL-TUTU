angular.module('clientApp').controller('subheader', function ($scope, user, $state, $stateParams) {
  var options = $scope.options = [];
  var names = $scope.names = {};
  $scope.$watch(function () {
    return user;
  }, function () {
    user.lectures.forEach(function (lecture) {
      $scope.options.push(lecture.id);
      $scope.names[lecture.id] = lecture.name;
    });
  });

  $scope.$watch('lecture', function (id) {
    if (!id)
      return;
    $state.go('lecture.detail', {id: id});
  });

  $scope.$watch(function () {
    return $stateParams.id;
  }, function (id) {
    $scope.lecture = id;
  });

});