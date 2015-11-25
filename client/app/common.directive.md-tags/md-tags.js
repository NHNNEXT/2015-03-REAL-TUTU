(function () {
  angular.module('clientApp').factory('tagPush', function () {
    return function (model) {
      model.add = model.push;

      model.push = function (chip) {
        if (angular.element($("md-virtual-repeat-container:not(.ng-hide) li[md-virtual-repeat].selected")).length)
          return;
        chip = transform(chip);
        var ignore = false;
        model.forEach(function (each) {
          if (each.id != undefined && each.id === chip.id)
            ignore = true;
          if (each.text === chip.text)
            ignore = true;
        });
        if (ignore)
          return;
        model.add(chip);

        function transform(chip) {
          if (angular.isObject(chip)) {
            return chip;
          }
          return {text: chip};
        }
      };
    };
  });

  angular.module('clientApp')
    .directive('mdTags', function (tagPush) {
      return {
        restrict: 'E',
        scope: {
          ngModel: '=',
          match: '=',
          placeholder: '@',
          readonly: '=',
          mdId: '=',
          type: '@'
        },
        bindToController: true,
        controllerAs: 'ctrl',
        templateUrl: '/common.directive.md-tags/md-tags.html',
        controller: tagCtrl
      };
    });


  /* @ngInject */
  function tagCtrl(http, $scope, tagPush) {
    var self = this;

    _init();


    function _init() {
      if (!self.ngModel)
        self.ngModel = [];

      self.result = [];
      var url = {};
      url.content = '/api/v1/tag/content';
      url.lecture = '/api/v1/tag/lecture';

      var init = false;
      $scope.$watch(function () {
        return self.ngModel;
      }, function (model) {
        if (!model)
          return;
        if (!init) {
          init = true;
          return;
        }
        http.post(url[self.type], {id: self.mdId, tags: self.ngModel}, true);
      }, true);
    }

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
