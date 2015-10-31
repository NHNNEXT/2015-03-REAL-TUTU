'use strict';

describe('Service: regex', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var regex;
  beforeEach(inject(function (_regex_) {
    regex = _regex_;
  }));

  it('should do something', function () {
    expect(!!regex).toBe(true);
  });

});
