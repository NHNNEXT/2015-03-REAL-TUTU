angular.module('clientApp')
  .directive('setTerm', function () {
    return {
      restrict: 'E',
      scope: {
        term: '='
      },
      bindToController: true,
      controllerAs: 'ctrl',
      templateUrl: '/term.directive.set-term/set-term.html',
      controller: function (Term, $scope) {
        this.querySearch = function (keyword) {
          if (!keyword)
            return;
          return Term.getList({keyword: keyword});
        };

        //var self = this;
        //
        //$scope.$watch(function () {
        //  return self.selectedItem;
        //}, function (item) {
        //  if (!item) {
        //    self.lecture.termId = undefined;
        //    return;
        //  }
        //  self.lecture.termId = item.id;
        //});

      }
    };
  });
