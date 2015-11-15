angular.module('clientApp')
  .factory('Page', function () {


    function Page(page, pageSize) {
      this.page = page === undefined ? 1 : page;
      this.pageSize = pageSize === undefined ? 10 : pageSize;
    }

    Page.prototype.next = function () {
      return this.page++;
    };

    Page.prototype.return = function (returned) {
      this.returned = returned;
    };

    Page.prototype.hasNext = function () {
      return this.returned === undefined || this.returned === this.pageSize;
    };

    return Page;
  });
