
angular.module('clientApp')
  .controller('scheduleContentController',function($filter,$rootScope,$scope,$stateParams,Lecture) {

    $rootScope.$on('userStateChange', function () {
      getLecture($stateParams.id);
    });
    $scope.$watch(function () {
      return $stateParams.id;
    }, getLecture);

    $scope.getLecture = getLecture;
    $scope.status = {
      isFirstOpen: true,
      isFirstDisabled: false
    };
    $scope.oneAtATime = true;

    function getLecture(id) {
      if (!id)
        return;
      Lecture.findById(id).then(function (fromDB) {
        $scope.lecture = fromDB;
        $scope.contents =$filter('contentGroup')(fromDB.contents,'수업');
      });
    }
  })
  .directive('scheduleContentList', function () {
    return {
      restrict: 'E',
      templateUrl: '/lecture.editor/schedule-content-list.html',
      scope: {
        close: '&'
      },
      controller: "scheduleContentController"
    //  link: function(scope,iEle,iAttrs) {
    //
    //    $scope.$watch(function () {
    //      return $stateParams.id;
    //    }, getLecture);
    //
    //    Lecture.findById
    //  }
    };
  });
