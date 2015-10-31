'use strict';

describe('Service: lectureSvc', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var lectureSvc;
  beforeEach(inject(function (_lectureSvc_) {
    lectureSvc = _lectureSvc_;
  }));

  it('should do something', function () {
    expect(!!lectureSvc).toBe(true);
  });

});
