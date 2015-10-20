'use strict';

describe('Controller: HomeworkCtrl', function () {

  // load the controller's module
  beforeEach(module('clientApp'));

  var HomeworkCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HomeworkCtrl = $controller('HomeworkCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    //expect(HomeworkCtrl.awesomeThings.length).toBe(3);
  });
});
