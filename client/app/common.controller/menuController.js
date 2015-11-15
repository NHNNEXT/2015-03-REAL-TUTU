angular.module('clientApp')
  .controller('menuController',
  /* @ngInject */
  function ($scope, dialog, rootUser) {
    $scope.login = dialog.login;
    $scope.register = dialog.register;
    $scope.rootUser = rootUser;
    $scope.section = {
      name: 'Beers',
      type: 'toggle',
      pages: [{
        name: 'IPAs',
        type: 'link',
        state: 'beers.ipas',
        icon: 'fa fa-group'
      }, {
        name: 'Porters',
        state: 'home.toollist',
        type: 'link',
        icon: 'fa fa-map-marker'
      },
        {
          name: 'Wheat',
          state: 'home.createTool',
          type: 'link',
          icon: 'fa fa-plus'
        }]
    };
  });
