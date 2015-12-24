/**Created by itmnext13 on 2015. 11. 30..*/
angular.module('clientApp')
  /* @ngInject */
  .factory('Specification', function ($log) {
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
    function Specification() {}

    Specification.prototype = {
      and: function (specification) {
        if (!(specification instanceof Specification)) {
          $log.error("invalid input:: AndQuery require query object. but you input " + specification.constructor.name + "instance.");
        } else {
          return new AndSpecification(this, specification);
        }
      },
      or: function (specification) {
        if (!(specification instanceof Specification)) {
          $log.error("invalid input:: AndQuery require query object. but you input " + specification.constructor.name + "instance.");
        } else {
          return new OrSpecification(this, specification);
        }
      },
      not: function () {
        return new NotSpecification(this);
      }
    };

    /**
     * @constructor
     * @implements {Specificable}
     * @augments Specification
     */
    function AndSpecification(x, y) {
      Specification.call(this);
      var one = x;
      var other = y;
      this.prototype = _.create(one.prototype, {
        'constructor': one.constructor
      });
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
      Specification.call(this);
      var one = x;
      var other = y;
      this.prototype = _.create(one.prototype, {
        'constructor': one.constructor
      });
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
      Specification.call(this);
      this.prototype = _.create(x.prototype, {
        'constructor': x.constructor
      });
      var wrapped = x;
      this.isSatisfiedBy = function (candidate) {
        return !wrapped.isSatisfiedBy(candidate);
      };
    }

    return Specification;
  });
