angular.module('clientApp')
  .directive('relativeContents', function () {
    return {
      restrict: 'E',
      scope: {
        content: '=',
        readonly: '=',
        relativeContents: '='
      },
      templateUrl: '/content.directive.relative-contents/relative-contents.html',
      bindToController: true,
      controllerAs: 'ctrl',
      controller: function (http, Content, responseCode, alert) {
        var self = this;

        this.querySearch = function (keyword) {
          if (!keyword)
            return;
          return Content.getList({lectureId: this.content.lectureId, keyword: keyword});
        };

        this.delete = function(item){
          http.delete('/api/v1/content/relative', {contentId: self.content.id, linkContentId: item.id}).then(function () {
            self.relativeContents.remove(item);
          });
        };



        this.select = function (item) {
          if (item === null)
            return;
          if (!self.relativeContents) {
            self.relativeContents = [];
          }
          http.post('/api/v1/content/relative', {contentId: self.content.id, linkContentId: item.id}).then(function () {
            self.relativeContents.push(new Content(item));
            self.searchText = "";
          }, function (response) {
            if (response.code === responseCode.ContentRelation.ALREADY_EXIST) {
              alert.warning("이미 추가되었습니다.");
              return;
            }
            if (response.code === responseCode.ContentRelation.CANT_BIND_SELF) {
              alert.warning("자기자신은 추가할 수 없습니다.");
            }
          });
        };
      }
    };
  });
