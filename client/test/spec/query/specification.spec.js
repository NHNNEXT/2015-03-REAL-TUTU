/**
 * Created by itmnext13 on 2015. 11. 30..
 */
'use strict';

describe('Factory: specificationFactory', function () {

  // load the service's module
  beforeEach(module('clientApp'));

  // instantiate service
  var specificationFactory;
  beforeEach(inject(function (_specificationFactory_) {
    specificationFactory = _specificationFactory_;
  }));

  it('should make and operate', function () {

    var scheduleSpec = specificationFactory.ContainerSpecification("SCHEDULE");
    var noticeSpec = specificationFactory.ContainerSpecification("NOTICE");

    var mainTopSpec = scheduleSpec.and(noticeSpec);


    expect(mainTopSpec).toBe(123);
  });
  //
  //it('should set specification', function() {
  //
  //});


});
