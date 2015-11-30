/**
 * Created by itmnext13 on 2015. 11. 30..
 */
angular.module('clientApp')
  /* @ngInject */
  .factory('Container', function (SpecificationTarget) {
    "use strict";
    /**
     * @constructor
     */
    function Container() {
      this.contentList = [];
      this.specificationList = [];
    }
    _.mixin(Container.prototype, new SpecificationTarget());
    _.mixin(Container.prototype, {
      constructor: Container,
      self: this,
      canContain: function(content) {
        return content.getSpecificationList().isSatisfiedBy(this.self);
      }
    });
    return Container;
  });
