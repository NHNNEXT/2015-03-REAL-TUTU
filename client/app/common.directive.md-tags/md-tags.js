(function () {
  angular.module('clientApp')
    .directive('mdTags', function () {
      return {
        restrict: 'E',
        scope: {
          ngModel: '=',
          match: '=',
          placeholder: '@'
        },
        bindToController: true,
        controllerAs: 'ctrl',
        templateUrl: '/common.directive.md-tags/md-tags.html',
        controller: tagCtrl
      };
    });


  /* @ngInject */
  function tagCtrl(http) {
    var self = this;
    this.ngModel = [];
    this.result = [];
    this.ngModel.add = this.ngModel.push;

    this.ngModel.push = function (chip) {
      if (angular.element($("md-virtual-repeat-container:not(.ng-hide) li[md-virtual-repeat].selected")).length) return;
      chip = transform(chip);
      self.ngModel.add(chip);

      function transform(chip) {
        if (angular.isObject(chip)) {
          return chip;
        }
        return {tag: chip}
      }
    };

    this.querySearch = function (keyword) {
      return http.get('/api/v1/tag', {keyword: keyword}).then(function (result) {
        if (self.result === null || self.result === "null")
          return;
        return result;
      });
    };

    this.search = function () {
      if (self.keyword === undefined || self.keyword === "") {
        self.result = [];
        return;
      }
      http.get(self.src, {keyword: self.keyword}).then(function (result) {
        if (self.result === null || self.result === "null")
          return;
        self.result = result;
      });
    };
  }

})();
