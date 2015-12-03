angular.module('clientApp')
  /* @ngInject */
  .factory('contentSpecificationFactory', function (Specification,contentFactory) {
    "use strict";

    var contentSpecificationList = {};
    /**
     * @constructor
     * @implements {Specificable}
     * @augments Specification
     * @param required
     */
    function ContentSpecification(required) {
      if(this instanceof ContentSpecification) {
        Specification.call(this);
        this.res = required;
        var requiredType = required;

        this.isSatisfiedBy = function(candidate) {
          if(!(candidate instanceof contentFactory.create)) {
            return false;
          }
          return _.includes(candidate.getTypeList(), requiredType);
        };
      } else {
        return new ContentSpecification(required);
      }
    }

    ContentSpecification.prototype = _.create(Specification.prototype, {
      'constructor': ContentSpecification
    });


    return {
      create: function(required) {
        // 저장소에 이미 있다면 그것을 리턴git a
        var cs = _.get(contentSpecificationList, required);
        if (typeof cs !== "undefined") {
          return cs;
        }
        cs = new ContentSpecification(required);
        _.set(contentSpecificationList, required, cs);
        return cs;
      }
    };
  });
