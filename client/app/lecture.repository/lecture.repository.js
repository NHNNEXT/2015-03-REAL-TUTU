//angular.module('clientApp')
//  /* @ngInject */
//  .service('lectureRepository', function (http, $sce, $q, $state, confirm, Reply, queryService, containerSpecificationFactory) {
//    "use strict";
//    this.findByContentID = findByContentID;
//    this.findByLectureID = findByLectureID;
//    this.save = save;
//    var scheduleSpec = containerSpecificationFactory.create("SCHEDULE");
//    //console.log(scheduleSpec);
//    //var noticeSepc = new ContainerSpecification("NOTICE");
//    //var todoSpec = new ContainerSpecification("SUBMIT");
//    //var mainTopSpec = scheduleSpec.and(noticeSepc);
//    //var idspec = new ListSpecification("")
//
//    //function selectSatisfying(contentSpecification) {
//    //  return contentSpecification.satisfyingElementsFrom(this);
//    //}
//
//    function findByContentID(contentID) {
//      return $q(function (resolve) {
//        //Restangular.one('content',contentID).get()
//        http.get('/api/v1/content', {id: contentID}).then(function (result) {
//          resolve(ContentFactory.ExistedContent(result));
//        });
//      });
//    }
//    function findByLectureID(lectureID) {
//      return $q(function (resolve) {
//        //Restangular.one('lecture',LectureID).getList('content');
//        http.get('/api/v1/content/list', {lectureID: lectureID}).then(function (response) {
//          var result = [];
//          if (response.forEach) {
//            response.forEach(function (each) {
//              result.push(ContentFactory.ExistedContent(each));
//            });
//          }
//          else {
//            return;
//          }
//          resolve(result);
//        });
//      });
//    }
//  });
