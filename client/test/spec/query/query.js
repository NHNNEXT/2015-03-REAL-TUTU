'use strict';

describe('Service: queryService', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var queryService;
  beforeEach(inject(function (_queryService_) {
    queryService = _queryService_;
  }));

  it('should make query', function () {
    var content;
    content = {
      id: 123,
      title: "test",
      body: "body",
      lectureId: 1,
      endTime: new Date("2012-06-09T15:20:00Z"),
      startTime: new Date("2011-06-09T15:20:00Z")
    };
    var contentQuery = new queryService.contentSave(content);
    expect(contentQuery.id).toBe(123);
  });
  //
  //it('should set specification', function() {
  //
  //});


});
