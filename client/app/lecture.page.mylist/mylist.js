/* @ngInject */
angular.module('clientApp').config(function ($stateProvider) {
  $stateProvider
    .state('mylectures', {
      url: "/mylectures",
      header: "내 강의",
      templateUrl: "/lecture.page.mylist/mylist.html",
      /* @ngInject */
      controller: function ($scope, rootUser, types, http) {
        $scope.rootUser = rootUser;
        $scope.majorTypes = types.majorTypes;
        $scope.sideToggleRequest = function(lecture){
          http.post('/api/v1/lecture/sideMenu', {lectureId:lecture.id}).then(function(result){
            lecture.sideMenu = result;
          });
        };
      }
    });
});
