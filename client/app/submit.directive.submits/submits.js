angular.module('clientApp')
  .directive('submits', function () {
    return {
      restrict: 'E',
      scope: {
        submitRequire: '='
      },
      templateUrl: '/submit.directive.submits/submits.html',
      /* @ngInject */
      controller: function (rootUser, $scope, Submit, User, emoticon, http, Page, alert, confirm) {

        $scope.more = function () {
          var query = {};
          query.userHaveToSubmitId = $scope.submitId;
          query.page = page.next();
          Submit.getList(query).then(function (submits) {
            page.return(submits.length);
            submits.forEach(function (submit) {
              $scope.submits.push(submit);
            });
          });
        };


        $scope.$watch('submitRequire', function (submitRequire) {
          if (!submitRequire)
            return;
          $scope.user = new User(submitRequire.user);
          $scope.submitId = submitRequire.id;
          $scope.more();
        });


        $scope.submits = [];
        var page = $scope.page = new Page();


        $scope.doToggle = function () {
          var state = !$scope.submitRequire.done;//NG-Model바뀌는거보다, 이벤트 실행이 먼저되서 !done으로 보내야함.

          if (state && $scope.submits.length === 0) {
            confirm("제출한 내역이 없습니다. 그래도 제출 완료상태로 바꾸시겠습니까?", "", function () {
              postToggle(state);
            }, function () {
              $scope.submitRequire.done = false;
            });
            return;
          }
          postToggle(state);
        };

        function postToggle(state) {
          http.post('/api/v1/content/submit/require', {id: $scope.submitId, done: state}).then(function () {
            if (state) {
              alert.info("완료 되었습니다. 제출 목록에 표시되지 않습니다.");
              return;
            }
            alert.info("제출 목록에 표시합니다.");
          });
        }


        $scope.submit = new Submit();
        $scope.rootUser = rootUser;
        $scope.writeSubmit = function (submit) {
          submit.submitId = $scope.submitId;
          submit.save().then(function (result) {
            $scope.submits.push(new Submit(result));
            if ($scope.user.id === result.writer.id)
              emoticon.submitDone();
            $scope.submit = new Submit();
          });
        };
      }
    };
  });
