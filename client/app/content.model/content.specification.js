angular.module('clientApp')
  /* @ngInject */
  .factory('ContentSpecification', function (Specification, Content) {
    "use strict";
    /**
     * @constructor
     * @implements {Specificable}
     * @augments Specification
     */
    function ContentSpecification(required) {
      var requiredType = required;
      this.specificationList = [];
      this.prototype = Specification.prototype;
      this.constructor = Specification.constructor;
      this.isSatisfiedBy = function (candidate) {
        if (!(candidate instanceof Content)) {
          return false;
        }
        return candidate.getSpecificationList().contains(requiredType);
      };
    }

    return ContentSpecification;
  });
