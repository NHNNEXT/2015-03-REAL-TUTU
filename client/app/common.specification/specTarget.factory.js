angular.module('clientApp')
  /* @ngInject */
  .factory('SpecificationTarget', function () {
    "use strict";
    function SpecificationTarget() {

      var spec;

      function setSpecification(specification) {
        if (!spec) {
          spec = specification;
        }
      }
      function getSpecification() {
        return spec;
      }
      this.setSpecification = setSpecification;
      this.getSpecification = getSpecification;
    }

    return SpecificationTarget;
  });
