angular.module('clientApp')
  .directive('lectureAdd', function () {
    return {
      restrict: 'E',
      scope: {
        allLectures: '=',
        node: '='
      },
      templateUrl: '/lecture.directive.lecture-node/lecture-add.html',
      bindToController: true,
      controllerAs: 'ctrl',
      /* @ngInject */
      controller: function (http) {
        var self = this;
        this.selectedItemChange = function (item) {
          if (!item)
            return;
          http.post('/api/v1/lecture/node/relation', {nodeId: self.node.id, lectureId: item.id}).then(function () {
            self.node.lectures.pushIfNotExist(item);
            self.new = false;
          });
        };
      }
    };
  });
