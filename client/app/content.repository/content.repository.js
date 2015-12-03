angular.module('clientApp')
  /* @ngInject */
  .service('ContentRepository', function (ContentFactory, http, $sce, $q, $state, confirm, Reply, queryService, containerSpecificationFactory) {
    "use strict";
    this.findByContentID = findByContentID;
    this.findByLectureID = findByLectureID;
    this.save = save;
    var scheduleSpec = containerSpecificationFactory.create("SCHEDULE");
    console.log(scheduleSpec);
    //var noticeSepc = new ContainerSpecification("NOTICE");
    //var todoSpec = new ContainerSpecification("SUBMIT");
    //var mainTopSpec = scheduleSpec.and(noticeSepc);
    //var idspec = new ContentSpecification("")

    //function selectSatisfying(contentSpecification) {
    //  return contentSpecification.satisfyingElementsFrom(this);
    //}

    function findByContentID(contentID) {
      return $q(function (resolve) {
        //Restangular.one('content',contentID).get()
        http.get('/api/v1/content', {id: contentID}).then(function (result) {
          resolve(ContentFactory.ExistedContent(result));
        });
      });
    }

    function findByLectureID(lectureID) {
      return $q(function (resolve) {
        //Restangular.one('lecture',LectureID).getList('content');
        http.get('/api/v1/content/list', {lectureID: lectureID}).then(function (response) {
          var result = [];
          if (response.forEach) {
            response.forEach(function (each) {
              result.push(ContentFactory.ExistedContent(each));
            });
          }
          else {
            return;
          }
          resolve(result);
        });
      });
    }

    //@todo: 나는 이걸 새로 고쳐야해
    function save(content) {
        return http.post('/api/v1/content', content);
    }
    //
    //function update(content) {
    //  return http.put('/api/v1/content', content);
    //}

    //function selectTodoTypeContent() {
    //  this.isSatisfiedBy = function(candidate) {
    //    if(!(candidate instanceof Content.Content)) return false;
    //    return candidate.getSpecificationList().contains(requiredSpec);
    //  };
    //}

    //
    //Content.prototype.remove = function () {
    //  if (!confirm("삭제하시겠습니까?"))
    //    return;
    //  var self = this;
    //  http.delete('/api/v1/content', {id: this.id}).then(function () {
    //    $state.go('lecture', {id: self.lectureId});
    //  });
    //};
    //
    //Content.getList = function () {
    //  return $q(function (resolve) {
    //    http.get('/api/v1/content/list').then(function (response) {
    //      var result = [];
    //      if (response.forEach === undefined)
    //        return;
    //      response.forEach(function (each) {
    //        result.push(new ContentFactory(each));
    //      });
    //      resolve(result);
    //    });
    //  });
    //};
    //

    //
    //function findByContentIDAtList(contentID) {
    //  var content = contentList[contentID];
    //  if(content) {
    //    return content;
    //  } else {
    //    contentList[contentID] =  JSON.parse(Restangular.one('content',contentID).get());
    //    return contentList[contentID];
    //  }
    //}
  });
