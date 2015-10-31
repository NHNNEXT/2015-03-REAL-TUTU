'use strict';

describe('Service: lecture', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var lecture;
  beforeEach(inject(function (_lecture_) {
    lecture = _lecture_;
  }));

  it('should do something', function () {
    expect(!!lecture).toBe(true);
  });

});
