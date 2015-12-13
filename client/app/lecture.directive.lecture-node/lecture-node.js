angular.module('clientApp')
  /* @ngInject */
  .directive('lectureNode', function (RecursionHelper) {
    return {
      restrict: 'E',
      scope: {
        allLectures: '=',
        node: '=',
        parent: '=',
        readonly: '='
      },
      compile: function (element) {
        // Use the compile function from the RecursionHelper,
        // And return the linking function(s) which it returns
        return RecursionHelper.compile(element);
      },
      templateUrl: '/lecture.directive.lecture-node/lecture-node.html',
      /* @ngInject */
      controller: function (http, $scope, confirm, responseCode, alert) {

        $scope.getDetail = getDetail;
        $scope.deleteLecture = deleteLecture;
        $scope.deleteThis = deleteThis;
        $scope.newNode = newNode;
        $scope.isDeletable = isDeletable;

        $scope.hasDetail = false;

        if (!$scope.node){
          $scope.node = {name: "NEXUS"};
          $scope.displayChildren = true;
          getDetail();
        }

        function isDeletable() {
          if (!$scope.node.id)
            return false;
          if (!$scope.hasDetail)
            return false;
          if ($scope.node.children.length > 0)
            return false;
          return $scope.node.lectures.length === 0;
        }

        function newNode() {
          http.post('/api/v1/lecture/node', {name: $scope.nodeName, parentId: $scope.node.id}).then(function (result) {
            $scope.new = false;
            $scope.node.children.push(result);
          });
        }

        function deleteThis() {
          confirm("삭제하시겠습니까?", "분류 : " + $scope.node.name, function () {
            http.delete('/api/v1/lecture/node', {
              lectureNodeId: $scope.node.id
            }).then(function () {
              $scope.parent.children.remove($scope.node);
            }, function (result) {
              if (result.code === responseCode.Node.CHILD_EXIST) {
                alert.warning("현재 분류에 속한 강의와 분류를 지운다움 시도하세요.");
              }
            });
          });
        }

        function deleteLecture(lecture) {
          confirm("분류에서 삭제합니다.", $scope.node.name + " : " + lecture.name,
            function () {
              http.delete('/api/v1/lecture/node/relation', {
                nodeId: $scope.node.id,
                lectureId: lecture.id
              }).then(function () {
                $scope.node.lectures.remove(lecture);
              });
            });
        }

        function getDetail() {
          if ($scope.hasDetail)
            return;
          http.get('/api/v1/lecture/node', {parentId: $scope.node.id}).then(function (result) {
            $scope.hasDetail = true;
            $scope.node.children = result.children;
            $scope.node.lectures = result.lectures;
          });
        }
      }
    };
  });
