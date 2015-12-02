'use strict';

describe('Service: contentRepository', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var ContentRepository;
  beforeEach(inject(function (_ContentRepository_) {
    ContentRepository = _ContentRepository_;
  }));

  //it('should make ProtoContent', function () {
  //  var content;
  //  content = {
  //    title: "test",
  //    body: "body",
  //    lectureId: 1,
  //    endTime: new Date("2012-06-09T15:20:00Z"),
  //    startTime: new Date("2011-06-09T15:20:00Z"),
  //    type: "NOTICE"
  //  };
  //  var newbie = new ContentFactory(content);
  //  expect(newbie.title).toBe("test");
  //});
  //
  //it('content have set,get spec', function () {
  //  var content;
  //  content = {
  //    title: "test",
  //    body: "body",
  //    lectureId: 1,
  //    endTime: new Date("2012-06-09T15:20:00Z"),
  //    startTime: new Date("2011-06-09T15:20:00Z"),
  //    type: "NOTICE"
  //  };
  //  var newbie = new ContentFactory(content);
  //  expect("getContainerSpecification" in newbie).toBe(true);
  //});
});
