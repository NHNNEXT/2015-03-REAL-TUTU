angular.module('clientApp')
  /* @ngInject */
  .factory('LectureNode', function (http, confirm, responseCode, alert, $q) {
    function LectureNode(param, parent) {
      if (param === undefined) {
        this.name = "NEXUS";
        this.level = 0;
        this.defaultOpenLevel = 2;
        this.getDetail();
        return;
      }
      if (typeof param === "object") {
        this.setProperties(param, parent);
      }
    }

    LectureNode.prototype.setProperties = function (param, parent) {
      this.id = param.id;
      this.name = param.name;
      this.defaultOpenLevel = parent.defaultOpenLevel;
      this.parent = parent;
      this.level = parent.level + 1;
      if (this.defaultOpenLevel < this.level)
        return;
      this.getDetail();
    };


    LectureNode.prototype.delete = function () {
      var self = this;
      confirm("삭제하시겠습니까?", "분류 : " + self.name, function () {
        http.delete('/api/v1/lecture/node', {
          lectureNodeId: self.id
        }).then(function () {
          self.parent.children.remove(self);
        }, function (result) {
          if (result.code === responseCode.Node.CHILD_EXIST) {
            alert.warning("현재 분류에 속한 것을 모두 지운다움 시도하세요.");
          }
        });
      });
    };

    LectureNode.prototype.deleteLecture = function (lecture) {
      var self = this;
      confirm("분류에서 삭제합니다.", self.name + " : " + lecture.name,
        function () {
          http.delete('/api/v1/lecture/node/relation', {
            nodeId: self.id,
            lectureId: lecture.id
          }).then(function () {
            self.lectures.remove(lecture);
          });
        });
    };
    LectureNode.prototype.addLecture = function (lecture) {
      var self = this;
      return $q(function (resolve, error) {
        self.getDetail().then(function () {
          if (self.lectures.findById(lecture.id)) {
            alert.warning("이미 있습니다.");
            error();
            return;
          }
          http.post('/api/v1/lecture/node/relation', {
            nodeId: self.id,
            lectureId: lecture.id
          }).then(function () {
            self.lectures.push(lecture);
            resolve();
          });
        });
      });
    };

    LectureNode.prototype.getDetail = function () {
      var self = this;
      return $q(function (resolve) {
        if (self.hasDetail) {
          resolve();
          return;
        }
        http.get('/api/v1/lecture/node', {parentId: self.id}).then(function (result) {
          self.hasDetail = true;
          self.displayChildren = true;
          self.children = [];
          result.children.forEach(function (node) {
            self.children.push(new LectureNode(node, self));
          });
          self.lectures = result.lectures;
          resolve();
        });
      });
    };


    LectureNode.prototype.isDeletable = function () {
      if (!this.id)
        return false;
      if (!this.hasDetail)
        return false;
      if (this.children.length > 0)
        return false;
      return this.lectures.length === 0;
    };

    LectureNode.prototype.newChild = function (nodeName) {
      var self = this;
      return $q(function (resolve) {
        http.post('/api/v1/lecture/node', {name: nodeName, parentId: self.id}).then(function (result) {
          self.children.push(new LectureNode(result, self));
          resolve();
        });
      });
    };


    return LectureNode;
  });
