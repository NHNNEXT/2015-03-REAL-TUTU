angular.module('clientApp')
  /* @ngInject */
  .factory('SpecificationTarget', function () {
    "use strict";
    function SpecificationTarget() {
    }

    SpecificationTarget.prototype = {
      constructor: SpecificationTarget,
      specificationList: [],

      /**
       * @param specification
       */
      setSpecification: function (specification) {
        this.specificationList.push(specification);
      },

      /**
       * @returns {Array}
       */
      getSpecificationList: function () {
        return this.specificationList;
      }
    };
    return SpecificationTarget;
  });
