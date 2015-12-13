angular.module('clientApp')
  /* @ngInject */
  .service('packerService', function () {
    "use strict";
    this.pack = pack;
    /**
     *
     * @param containers
     * @param candidates
     */
    function pack(containers, candidates) {
      _.forEach(candidates, function (candidate) {
        var container = findContainerFor(containers, candidate);
        container.contentList.push(candidate);
      });
    }

    /**
     * @param containers
     * @param candidate
     * @returns {container}
     */
    function findContainerFor(containers, candidate) {
      var len = containers.length;
      var res;
      for(var i=0; i<len; i++){
        if(containers[i].canContain(candidate)) {
          res = containers[i];
          break;
        }
      }
      return res;
    }
  });
