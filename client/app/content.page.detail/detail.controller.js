angular.module('clientApp').controller('contentDetailController',
  /* @ngInject */
  function ($scope, rootUser, Content, $stateParams, types, Reply, $state, http, $rootScope) {
    $scope.rootUser = rootUser;
    $scope.content = {replies: []};

    $scope.getRelativeIcon = function () {
      if ($scope.relativeReadOnly)
        return "/resource/icon/relative.svg";
      return "/resource/icon/done.svg";
    };

    $scope.getTagIcon = function () {
      if ($scope.tagReadOnly)
        return "/resource/icon/tag.svg";
      return "/resource/icon/done.svg";
    };

    $rootScope.$on('userStateChange', function(){
      getContent($stateParams.id);
    });

    $scope.$watch(function () {
      return $stateParams.id;
    }, function (id) {
      getContent(id);
    });
    function getContent(id) {
      Content.findById(id).then(function (content) {
        $scope.content = content;

        $scope.tagReadOnly = true;
        $scope.relativeReadOnly = true;

        $scope.$watch(function () {
          return $scope.content.tags;
        }, function () {
          if ($scope.tagReadOnly)
            return;
          http.post('/api/v1/tag/content', {id: $scope.content.id, tags: $scope.content.tags}, true);
        }, true);

        $state.current.header = content.lectureName;
        $state.current.headerClick = function () {
          $state.go('lecture', {id: $scope.content.lectureId});
        };
      });
    }
  });
