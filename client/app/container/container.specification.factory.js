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
        if(Array.isArray(required)) {
          if(required.length === 1) {
            var temp;
            var require = required[0];
            temp = _.get(containerSpecificationList, require);
            if (typeof temp !== "undefined") {
              return temp;
            }
            temp = new ContainerSpecification(require);
            _.set(containerSpecificationList, require, temp);
            return temp;
          } else {
            var csList = [];
            var cs;
            _.forEach(required,function(req) {
              cs = _.get(containerSpecificationList, req);
              if (typeof cs !== "undefined") {
                csList.push(cs);
              } else {
                cs = new ContainerSpecification(req);
                _.set(containerSpecificationList, req, cs);
                csList.push(cs);
              }
            });
            var total = _.reduce(csList, function(a, b) {
              return a.and(b);
            });
            return total;
          }
        } else {
          var cspec;
          cspec = _.get(containerSpecificationList, required);
          if (typeof cspec !== "undefined") {
            return cspec;
          }
          cspec = new ContainerSpecification(required);
          _.set(containerSpecificationList, required, cspec);
          return cspec;
        }
      }
    };
  });
