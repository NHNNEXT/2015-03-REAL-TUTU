angular.module('clientApp')
  /* @ngInject */
  .factory('contentFactory', function (containerSpecificationFactory) {
    "use strict";

    //can set,get containerSpec

    /**
     * @class
     */
    function Content(obj) {
      if(this instanceof Content){
        this.lectureId = obj.lectureId;
        this.title = obj.title;
        this.body = obj.body;
        this.endTime = obj.endTime;
        this.startTime = obj.startTime;
        this.type = obj.type;
      } else {
        return new Content(obj);
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

    Content.prototype = {
      setContainerSpecification: setContainerSpecification,
      getContainerSpecification: getContainerSpecification
      //makeQuery: makeQuery
    };

    //type: [notice,submit,schedule,default]
    /**
     * @class
     * @augments Content
     */
    function ExistedContent(obj) {
      if(this instanceof ExistedContent){
        Content.call(this,obj);
        this.id = obj.id;
        this.repliesSize = obj.repliesSize;
        this.submitsSize = obj.submitsSize;
        this.writer = obj.writer;
        this.lectureName = obj.lectureName;
        this.tags = obj.tags === undefined ? [] : obj.tags;
        if (obj.writeDate) {
          this.writeDate = new Date(obj.writeDate);
        }
        if (obj.startTime) {
          this.startTime = new Date(obj.startTime);
        }
        if (obj.endTime) {
          this.endTime = new Date(obj.endTime);
        }
        this.type = obj.type; //타입오브젝트
        this.hits = obj.hits;
        this.likes = obj.likes;
        setContainerSpecification(containerSpecificationFactory.create(this.type));
      } else {
        new ExistedContent(obj);
      }
    }

    ExistedContent.prototype = _.create(Content.prototype, {
      'constructor': ExistedContent
    });

    return {
      create: function(obj) {
        if(obj.hasOwnProperty("id")) {
          return new ExistedContent(obj);
        }
        return new Content(obj);
      }
    };
  });
