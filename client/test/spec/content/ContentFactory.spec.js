/**
 * Created by itmnext13 on 2015. 12. 2..
 */
'use strict';

describe('Factory: contentFactory', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var contentFactory;
  beforeEach(inject(function (_contentFactory_) {
    contentFactory = _contentFactory_;
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
    var newbie = contentFactory.create(content);
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
    var newbie =  contentFactory.create(content);
    expect("getContainerSpecification" in newbie).toBe(true);
  });

});
