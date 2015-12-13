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
      controller: function () {
        var self = this;
        this.selectedItemChange = function (item) {
          if (!item)
            return;
          self.node.addLecture(item).then(function(){
            self.new = false;
            self.searchText = "";
            self.selectedItem = undefined;
          }, function(){
            self.searchText = "";
            self.selectedItem = undefined;
          });
        };
      }
    };
  });
