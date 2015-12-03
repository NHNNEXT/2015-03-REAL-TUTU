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
      controller: function (Term) {
        this.querySearch = function (keyword) {
          if (!keyword)
            return;
          return Term.getList({keyword: keyword});
        };
      }
    };
  });
