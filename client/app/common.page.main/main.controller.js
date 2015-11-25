(function () {

  angular
    .module('clientApp')
    /* @ngInject */
    .controller('mainController', function (rootUser, Content, $state, $scope) {

      var self = this;

      self.tags = [];
      self.selectedTags = [];

      this.hasTag = function (contents) {
        if (!contents)
          return;
        if (self.selectedTags.length === 0)
          return contents;
        var result = [];
        contents.forEach(function (content) {
          for (var i = 0; i < self.selectedTags.length; i++) {
            if (!content.tags.find(function (tag) {
                return tag.text === self.selectedTags[i];
              }))
              return;
          }
          result.push(content);
        });
        return result;
      };

      $scope.$watch(function () {
        return rootUser.id;
      }, function (id) {
        if (id !== undefined)
          return;
        $state.go('loginneed');
      });
      self.rootUser = rootUser;
      var classes = ['gray', 'green', 'yellow', 'blue', 'purple', 'red'];
      Content.getList().then(function (list) {
        self.contents = list;
        list.forEach(function (content, i) {
          content.tags.forEach(function (tag) {
            self.tags.pushIfNotExist(tag.text);
          });
          content.row = 2;
          content.col = 2;
          content.class = classes[i % classes.length];
        });
      });
    });
})();
