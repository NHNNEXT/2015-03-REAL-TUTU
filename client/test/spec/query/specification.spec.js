'use strict';

describe('Factory: containerSpecificationFactory', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var containerSpecificationFactory;
  var contentFactory;
  var Specification;
  var Container;
  beforeEach(inject(function (_Specification_,_containerSpecificationFactory_,_contentFactory_,_Container_) {
    containerSpecificationFactory = _containerSpecificationFactory_;
    contentFactory = _contentFactory_;
    Specification = _Specification_;
    Container = _Container_;
  }));

  it('should make Object Specification', function () {
    var scheduleSpec = containerSpecificationFactory.create("SCHEDULE");
    expect(scheduleSpec.hasOwnProperty("isSatisfiedBy")).toBe(true);
  });

  it("should content set specification", function () {
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
    var scheduleSpec = containerSpecificationFactory.create("SCHEDULE");
    newbie.setContainerSpecification(scheduleSpec);
    expect(newbie.getContainerSpecification()).toBe(scheduleSpec);
  });

  it('should operate Specification', function () {

    var scheduleSpec = containerSpecificationFactory.create("SCHEDULE");
    var submitSpec = containerSpecificationFactory.create("SUBMIT");
    var mixSpec = scheduleSpec.or(submitSpec);
    expect(mixSpec.hasOwnProperty("isSatisfiedBy")).toBe(true);
  });

  it('should Container and Content Communicate via specification.', function () {
    var content;
    content = {
      title: "test",
      body: "body",
      lectureId: 1,
      endTime: new Date("2012-06-09T15:20:00Z"),
      startTime: new Date("2011-06-09T15:20:00Z"),
      type: "SCHEDULE"
    };

    var container1 = new Container();
    container1.setType(["SCHEDULE"]);

    var newbie1 = contentFactory.create(content);
    var scheduleSpec = containerSpecificationFactory.create("SCHEDULE");
    newbie1.setContainerSpecification(scheduleSpec);
    expect(container1.canContain(newbie1)).toBe(true);


  });


  it('should Specification AND Operator reject only one specification.', function () {
    var content;
    content = {
      title: "test",
      body: "body",
      lectureId: 1,
      endTime: new Date("2012-06-09T15:20:00Z"),
      startTime: new Date("2011-06-09T15:20:00Z"),
      type: "SCHEDULE"
    };

    var container1 = new Container();
    container1.setType(["SCHEDULE","SUBMIT"]);

    var scheduleSpec = containerSpecificationFactory.create("SCHEDULE");
    var submitSpec = containerSpecificationFactory.create("SUBMIT");
    var noticeSpec = containerSpecificationFactory.create("NOTICE");

    var mixSpec = scheduleSpec.and(submitSpec);
    var newbie1 = contentFactory.create(content);
    var newbie2 = contentFactory.create(content);
    var newbie3 = contentFactory.create(content);

    newbie1.setContainerSpecification(submitSpec);
    newbie2.setContainerSpecification(mixSpec);
    newbie3.setContainerSpecification(noticeSpec);

    expect(container1.canContain(newbie1)).toBe(true);
    expect(container1.canContain(newbie2)).toBe(true);
    expect(container1.canContain(newbie3)).toBe(false);


  });

  it('should Specification OR Operator work correctly.', function () {
    var content;
    content = {
      title: "test",
      body: "body",
      lectureId: 1,
      endTime: new Date("2012-06-09T15:20:00Z"),
      startTime: new Date("2011-06-09T15:20:00Z"),
      type: "SCHEDULE"
    };
    var container1 = new Container();

    container1.setType("SUBMIT_1");

    var submitSpec1 = containerSpecificationFactory.create("SUBMIT_1");
    var submitSpec2 = containerSpecificationFactory.create("SUBMIT_2");
    var either = submitSpec1.or(submitSpec2);
    var newbie1 = contentFactory.create(content);
    newbie1.setContainerSpecification(either);
    expect(container1.canContain(newbie1)).toBe(true);
  });


  it('should Specification create by array param.', function () {
    var content;
    content = {
      title: "test",
      body: "body",
      lectureId: 1,
      endTime: new Date("2012-06-09T15:20:00Z"),
      startTime: new Date("2011-06-09T15:20:00Z"),
      type: "SCHEDULE"
    };
    var container1 = new Container();
    container1.setType("SUBMIT_1");
    container1.setType("SUBMIT_2");
    var submitSpec1 = containerSpecificationFactory.create(["SUBMIT_1","SUBMIT_2"]);
    var newbie1 = contentFactory.create(content);
    newbie1.setContainerSpecification(submitSpec1);
    expect(container1.canContain(newbie1)).toBe(true);
  });
});
