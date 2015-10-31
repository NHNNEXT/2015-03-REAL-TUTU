'use strict';

describe('Service: lectureManager', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var lectureManager;
  beforeEach(inject(function (_lectureManager_) {
    lectureManager = _lectureManager_;
  }));

  it('should do something', function () {
    expect(!!lectureManager).toBe(true);
  });

});
