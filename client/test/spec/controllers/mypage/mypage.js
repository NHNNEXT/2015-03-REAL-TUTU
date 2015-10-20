'use strict';

describe('Controller: MypageCtrl', function () {

  // load the controller's module
  beforeEach(module('clientApp'));

  var MypageCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MypageCtrl = $controller('MypageCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    //expect(MypageCtrl.awesomeThings.length).toBe(3);
  });
});
