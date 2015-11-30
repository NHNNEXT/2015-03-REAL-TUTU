/**
 * Created by itmnext13 on 2015. 11. 30..
 */
angular.module('clientApp')
  /* @ngInject */
  .service('Packer', function (http) {
    var BASEURL = '/api/v1/';
    var CONTENT = BASEURL + 'content';
    this.contentSave = contentSave;

    function contentSave(content) {
      var query, res;
      query = {
        title: content.title,
        body: content.body,
        lectureId: content.lectureId,
        endTime: content.endTime,
        startTime: content.startTime
      };
      if(!content.id) {
        query.type = content.type;
        res = http.post(CONTENT, query);
      } else {
        query.id = content.id;
        res = http.put(CONTENT, query);
      }
      return res;
    }
  });
