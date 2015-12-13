angular.module('clientApp')
  /* @ngInject */
  .factory('listFactory', function (containerSpecificationFactory) {
    "use strict";
    //can set,get containerSpec
    /**
     * @class
     */
    function List(obj) {
      if(this instanceof List){
        this.type = [];
      } else {
        return new List(obj);
      }
    }

    function setContainerSpecification(specification) {
      if (!this.spec) {
      this.spec = specification;
      }
    }

    function getContainerSpecification() {
      return this.spec;
    }

    List.prototype = {
      setContainerSpecification: setContainerSpecification,
      getContainerSpecification: getContainerSpecification
    };

    //type: [notice,submit,schedule,default]
    //lecture({id:lecture.id})

    /**
     * @class
     * @augments obj
     */
    function LectureList(obj) {
      if(this instanceof LectureList){
        List.call(this,obj);
        this.type.push("lecture");
        this.name = obj.name;
        this.url = "lecture/" + obj.id;
        if(obj.sideMenu !== null) {
          this.type.push("favorite");
        }else {
          this.type.push("normal");
        }
        this.setContainerSpecification(containerSpecificationFactory.create(this.type));
      } else {
        new LectureList(obj);
      }
    }

    LectureList.prototype = _.create(List.prototype, {
      'constructor': LectureList
    });

    /**
     * @class
     * @augments obj
     */
    function UserList(obj) {
      if(this instanceof UserList){
        List.call(this,obj);
        this.type.push("person");
        this.name = obj.name;
        this.url = "profile/" + obj.id;
        //this.type.push("favorite");
        this.setContainerSpecification(containerSpecificationFactory.create(this.type[0]));
      } else {
        new UserList(obj);
      }
    }

    UserList.prototype = _.create(List.prototype, {
      'constructor': UserList
    });

    return {
      create: function(obj) {
        if(obj.hasOwnProperty("sideMenu")) {
          return new LectureList(obj);
        }
        if(obj.hasOwnProperty("email")) {
          return new UserList(obj);
        }
        return new List(obj);
      }
    };
  });
