angular.module('clientApp')
  /* @ngInject */
  .factory('containerSpecificationFactory', function (Specification,Container) {
    "use strict";

    /**
     * {{NOTICE: *, SUBMIT: *, SCHEDULE: *, DEFAULT: *}}
     */

    var types = {};
    /**
     * @class
     * @implements {Specificable}
     * @augments Specification
     */
    function ContainerSpecification(required) {
      var requiredType = required;
      this.specificationList = [];
      this.prototype = Specification.prototype;
      this.constructor = Specification.constructor;
      this.isSatisfiedBy = function(candidate) {
        if(!(candidate instanceof Container)) {
          return false;
        }
        return candidate.getSpecificationList().contains(requiredType);
      };
    }

    return {
      create: function(required) {
        var type = types[required];
        // 저장소에 이미 있다면 그것을 리턴
        if (type) {
          return type;
        }
        // 없다면 생성하고 저장소에 저장
        else {
          type[required] = new ContainerSpecification(required);
          return type[required];
        }
      }
    };
  });
