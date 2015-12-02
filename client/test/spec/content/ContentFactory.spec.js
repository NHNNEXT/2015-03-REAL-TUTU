/**
 * Created by itmnext13 on 2015. 12. 2..
 */
'use strict';

describe('Factory: ContentFactory', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var ContentFactory;
  beforeEach(inject(function (_ContentFactory_) {
    ContentFactory = _ContentFactory_;
  }));

  it('should make ProtoContent', function () {
    var content;
    content = {
      title: "test",
      body: "body",
      lectureId: 1,
      endTime: new Date("2012-06-09T15:20:00Z"),
      startTime: new Date("2011-06-09T15:20:00Z"),
      type: "NOTICE"
    };
   var newbie = new ContentFactory(content);
    expect(newbie.title).toBe("test");
  });

  it('content have set,get spec', function () {
    var content;
    content = {
      title: "test",
      body: "body",
      lectureId: 1,
      endTime: new Date("2012-06-09T15:20:00Z"),
      startTime: new Date("2011-06-09T15:20:00Z"),
      type: "NOTICE"
    };
    var newbie = new ContentFactory(content);
    expect("getContainerSpecification" in newbie).toBe(true);
  });

});
