angular.module('clientApp')
  /* @ngInject */
  .service('packerService', function () {
    "use strict";
    this.pack = pack;
    /**
     *
     * @param containers
     * @param contents
     */
    function pack(containers, contents) {
      _.forEach(contents, function (content) {
        var container = findContainerFor(containers, content);
        container.add(content);
      });
    }
    /**
     * @param containers
     * @param content
     * @returns {container}
     */
    function findContainerFor(containers, content) {
      _.forEach(containers, function (container) {
        if (container.canContain(content)) {
          return container;
        }
      });
      throw new Error("No container Exist.");
    }
  });
