angular.module('clientApp')
  /* @ngInject */
  .factory('ContentFactory', function (containerSpecificationFactory, SpecificationTarget) {
    "use strict";
    //var lectureList = {};
    //function lectureIDMapper(lectureName) {
    //  var lecture = lectureList[lectureName];
    //  if (lecture) {
    //    return lecture;
    //  }
    //  else {
    //    lectureList[lectureName] = obj.lectureID;
    //    return lectureList[lectureName];
    //  }
    //}
    /**
     * @class
     * @abstract
     */
    function Content(obj) {
      var content;
      content = new ProtoContent(obj);
      if (obj.id) {
        content = new ExistContent(content, obj);
      }
      return content;
    }

    _.mixin(Content.prototype, new SpecificationTarget());
    _.mixin(Content.prototype, {constructor: Content});

    /**
     * @class
     * @augments Content
     */
    function ProtoContent(obj) {
      this.prototype = Content.prototype;
      this.constructor = Content;
      this.lectureId = obj.lectureId;
      this.title = obj.title;
      this.body = obj.body;
      this.endTime = obj.endTime;
      this.startTime = obj.startTime;
    }

    /**
     * @class
     * @augments Content
     */
    //type: [notice,submit,schedule,default]
    function ExistContent(proto, obj) {
      proto.repliesSize = obj.repliesSize;
      proto.submitsSize = obj.submitsSize;
      proto.writer = obj.writer;
      proto.lectureName = obj.lectureName;
      proto.tags = obj.tags === undefined ? [] : obj.tags;
      if (obj.writeDate) {
        proto.writeDate = new Date(obj.writeDate);
      }
      if (obj.startTime) {
        proto.startTime = new Date(obj.startTime);
      }
      if (obj.endTime) {
        proto.endTime = new Date(obj.endTime);
      }
      proto.type = obj.type; //타입오브젝트
      proto.hits = obj.hits;
      proto.likes = obj.likes;
      proto.setSpecification(containerSpecificationFactory.create(proto.type));
      return proto;
    }

    return Content;
  });
