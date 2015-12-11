(function () {
  var scope;
  angular.module('clientApp')
    .service('emoticon', function ($timeout) {
      var self = this;
      this.submitDone = function () {
        scope.src = "/resource/emoticon/submitdone.gif";
        self.showing();
      };

      this.dontDoThat = function () {
        scope.src = "/resource/emoticon/dont.gif";
        self.showing();
      };

      this.showing = function () {
        scope.show = true;
        $timeout(function () {
          scope.show = false;
        }, 3000);
      };
    })
    .directive('emoticon', function () {
      return {
        restrict: 'E',
        templateUrl: "/common.service.emoticon/emoticon.html",
        controller: function (emoticon, $scope) {
          scope = $scope;
          $scope.emoticon = emoticon;
          $scope.hide = function () {
            $scope.show = false;
          };
        }
      };
    });
})();
