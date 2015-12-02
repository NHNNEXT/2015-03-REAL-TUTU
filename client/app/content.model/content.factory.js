angular.module('clientApp')
  /* @ngInject */
  .factory('ContentFactory', function (containerSpecificationFactory) {
    "use strict";

    //can set,get containerSpec
    /**
     * @class
     * @abstract
     */
    function Content(obj) {
      this.lectureId = obj.lectureId;
      this.title = obj.title;
      this.body = obj.body;
      this.endTime = obj.endTime;
      this.startTime = obj.startTime;
      this.type = obj.type;
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
    };

    /**
     * @class
     * @augments Content
     */
    //type: [notice,submit,schedule,default]
    function ExistedContent(obj) {
      Content.call(this,obj);
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
    }

    ExistedContent.prototype = _.create(Content.prototype, {
      'constructor': ExistedContent
    });

    return Content;
  });
