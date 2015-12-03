angular.module('clientApp')
  /* @ngInject */
  .factory('Container', function () {
    "use strict";

    /**
     * @constructor
     */
    function Container() {
      if(this instanceof Container) {
        this.contentList = [];
        this.typeList = [];
      } else {
        return new Container();
      }
    }

    Container.prototype = {
      canContain: function(content) {
        return content.getContainerSpecification().isSatisfiedBy(this);
      },
      getTypeList: function() {
        return this.typeList;
      },
      setType: function(required) {
        var typeList = this.typeList;
        if(Array.isArray(required)) {
          _.forEach(required,function(type) {
            if (!_.includes(typeList, type)) {
              typeList.push(type);
            }
          });
        }else {
          if (!_.includes(typeList, required)) {
            typeList.push(required);
          }
        }
      }
    };
    return Container;
  });
