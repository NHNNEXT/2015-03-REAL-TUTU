'use strict';

describe('Controller: UsermodifyCtrl', function () {

  // load the controller's module
  beforeEach(module('clientApp'));

  var UsermodifyCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    UsermodifyCtrl = $controller('UsermodifyCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    //expect(UsermodifyCtrl.awesomeThings.length).toBe(3);
  });
});
