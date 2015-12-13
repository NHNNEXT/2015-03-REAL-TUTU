angular.module('clientApp')
  /* @ngInject */
  .directive('lectureNode', function (RecursionHelper) {
    return {
      restrict: 'E',
      scope: {
        allLectures: '=',
        node: '=',
        readonly: '='
      },
      compile: function (element) {
        // Use the compile function from the RecursionHelper,
        // And return the linking function(s) which it returns
        return RecursionHelper.compile(element);
      },
      templateUrl: '/lecture.directive.lecture-node/lecture-node.html',
      /* @ngInject */
      controller: function (http, $scope, LectureNode) {

        $scope.newNode = function(nodeName){
          $scope.node.newChild(nodeName).then(function(){
            $scope.new = false;
          });
        };

        $scope.hasDetail = false;

        if (!$scope.node) {
          $scope.node = new LectureNode();
        }

        $scope.style = {fontSize: 100 - $scope.node.level * 10 + '%'};

      }
    };
  });
