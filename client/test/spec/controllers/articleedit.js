'use strict';

describe('Controller: ArticleeditCtrl', function () {

  // load the controller's module
  beforeEach(module('clientApp'));

  var ArticleeditCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ArticleeditCtrl = $controller('ArticleeditCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ArticleeditCtrl.awesomeThings.length).toBe(3);
  });
});
