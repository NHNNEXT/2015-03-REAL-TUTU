angular.module('clientApp')
  .controller('letterController',
    /* @ngInject */
    function (Letter, Page) {

      var page = this.page = new Page();

      var self = this;

      if (!self.letters) {
        self.letters = [];
      }

      self.more = function () {
        Letter.getList(page.next()).then(function (letters) {
          page.return(letters.length);
          letters.forEach(function (letter) {
            if (self.letters === undefined)
              self.letters = [];
            self.letters.push(letter);
          });
        });
      };

      self.more();

    });

