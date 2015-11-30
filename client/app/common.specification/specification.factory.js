/**Created by itmnext13 on 2015. 11. 30..*/
angular.module('clientApp')
  /* @ngInject */
  .factory('specificationFactory', function ($log, SpecificationTarget) {
    "use strict";
    /**
     * @interface Specificable
     */
    /**
     * @function
     * @name Specificable#isSatisfiedBy
     * @param candidate
     * @return {boolean}
     */

    /**
     * @constructor
     * @abstract
     */
    function Specification() {
    }

    _.mixin(Specification.prototype, new SpecificationTarget());
    _.mixin(Specification.prototype, {
      constructor: Specification,
      self: this,
      and: function (specification) {
        if (!(specification instanceof Specification)) {
          $log.error("invalid input:: AndQuery require query object. but you input " + specification.constructor.name + "instance.");
        } else {
          return new AndSpecification(this.self, specification);
        }
      },

      or: function (specification) {
        if (!(specification instanceof Specification)) {
          $log.error("invalid input:: AndQuery require query object. but you input " + specification.constructor.name + "instance.");
        } else {
          return new OrSpecification(this.self, specification);
        }
      },
      not: function () {
        return new NotSpecification(this.self);
      }
    });
    /**
     * @constructor
     * @implements {Specificable}
     * @augments Specification
     */
    function AndSpecification(x, y) {
      var one = x;
      var other = y;
      this.isSatisfiedBy = function (candidate) {
        return one.isSatisfiedBy(candidate) && other.isSatisfiedBy(candidate);
      };
    }

    /**
     * @constructor
     * @implements {Specificable}
     * @augments Specification
     */
    function OrSpecification(x, y) {
      var one = x;
      var other = y;
      this.isSatisfiedBy = function (candidate) {
        return one.isSatisfiedBy(candidate) || other.isSatisfiedBy(candidate);
      };
    }

    /**
     * @constructor
     * @implements {Specificable}
     * @augments Specification
     */
    function NotSpecification(x) {
      var wrapped = x;
      this.isSatisfiedBy = function (candidate) {
        return !wrapped.isSatisfiedBy(candidate);
      };
    }

    return Specification;
  });
