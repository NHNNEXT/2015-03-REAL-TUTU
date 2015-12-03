angular.module('clientApp')
  .factory('Term', function (http, $q) {
    var Term = function (obj) {
      if (obj === undefined)
        return;
      if (typeof obj !== "object") {
        return;
      }
      this.setProperties(obj);
    };

    Term.prototype.setProperties = function (obj) {
      this.id = obj.id;
      this.name = obj.name;
      this.start = new Date(obj.start);
      this.end = new Date(obj.end);
    };

    Term.prototype.save = function () {
      var query = {};
      query.name = this.name;
      query.start = this.start;
      query.end = this.end;
      return http.post('/api/v1/term', query);
    };

    Term.getList = function (query) {
      return $q(function (resolve) {
        http.get('/api/v1/term', query).then(function (result) {
          if (result === undefined || result.forEach === undefined)
            return;
          var terms = [];
          result.forEach(function (reply) {
            terms.push(new Term(reply));
          });
          resolve(terms);
        });
      });

    };

    return Term;
  });
