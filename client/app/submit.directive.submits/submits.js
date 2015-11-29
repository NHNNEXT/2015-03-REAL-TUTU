angular.module('clientApp')
  .directive('submits', function () {
    return {
      restrict: 'E',
      scope: {
        content: '='
      },
      templateUrl: '/submit.directive.submits/submits.html',
      /* @ngInject */
      controller: function (rootUser, $scope, Submit) {
        $scope.submits = [];
        $scope.more = function () {
          var query = {};
          query.contentId = $scope.content.id;
          query.page = $scope.page;
          Submit.getList(query).then(function (submits) {
            $scope.page++;
            $scope.moreSubmitExist = submits.length === 10;
            submits.forEach(function (submit) {
              $scope.submits.push(submit);
            });
          });
        };

        $scope.page = 0;
        $scope.$watch('content', function (id) {
          if (!id)
            return;
          $scope.more();
        });

        $scope.submit = new Submit();
        $scope.rootUser = rootUser;
        $scope.writeSubmit = function (submit) {
          submit.contentId = $scope.content.id;
          submit.save().then(function (result) {
            submit.id = result.id;
            $scope.content.submitsSize++;
            $scope.submits.push(submit);
            $scope.submit = new Submit();
            $scope.submit.contentId = $scope.content.id;
          });
        };
      }
    };
  });
