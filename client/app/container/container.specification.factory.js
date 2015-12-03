angular.module('clientApp')
  /* @ngInject */
  .factory('containerSpecificationFactory', function (Specification,Container) {
    "use strict";

    var containerSpecificationList = {};
    /**
     * @constructor
     * @implements {Specificable}
     * @augments Specification
     * @param required
     */
    function ContainerSpecification(required) {
      if(this instanceof ContainerSpecification) {
        Specification.call(this);
        this.res = required;
        var requiredType = required;

        this.isSatisfiedBy = function(candidate) {
          if(!(candidate instanceof Container)) {
            return false;
          }
          return _.includes(candidate.getTypeList(), requiredType);
        };
      } else {
        return new ContainerSpecification(required);
      }
    }

    ContainerSpecification.prototype = _.create(Specification.prototype, {
      'constructor': ContainerSpecification
    });

    return {
      create: function(required) {
        // 저장소에 이미 있다면 그것을 리턴
        var cs = _.get(containerSpecificationList, required);
        if (typeof cs !== "undefined") {
          return cs;
        }
        cs = new ContainerSpecification(required);
        _.set(containerSpecificationList, required, cs);
        return cs;
      }
    };
  });
