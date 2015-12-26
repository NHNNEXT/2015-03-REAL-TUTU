angular.module('clientApp')
  .directive('submitRequires', function () {
    return {
      restrict: 'E',
      scope: {},
      templateUrl: "/common.page.main/submit-requires/submit-requires.html",
      /* @ngInject */
      controller: function ($scope, http, Content, $rootScope, rootUser) {
        $scope.page = 0;

        $scope.submitRequires = [];

        $scope.doneCount = function (submitRequires) {
          var done = 0;
          submitRequires.forEach(function (submitRequire) {
            if (submitRequire.done)
              done++;
          });
          return done;
        };

        $scope.getMore = function () {
          if (!rootUser.isLogged())
            return;
          http.get('/api/v1/content/submit/require', {page: $scope.page}).then(function (results) {
            results.forEach(function (result) {
              $scope.submitRequires.push(new SubmitRequire(result));
            });
            $scope.more = results.length === 10;
            $scope.page++;
          });
        };

        $scope.getMore();

        function SubmitRequire(param) {
          this.done = param.done;
          this.content = new Content(param.content);
          this.id = param.id;
        }

        $rootScope.$on('userStateChange', $scope.getMore);

        SubmitRequire.prototype.doToggle = function () {
          http.post('/api/v1/content/submit/require', {id: this.id, done: !this.done}); //NG-Model바뀌는거보다, 이벤트 실행이 먼저되서 !done으로 보내야함.
        };

      }
    };
  });
