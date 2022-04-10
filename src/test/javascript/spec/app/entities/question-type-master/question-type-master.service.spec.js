"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var testing_2 = require("@angular/common/http/testing");
var question_type_master_service_1 = require("app/entities/question-type-master/question-type-master.service");
var question_type_master_model_1 = require("app/shared/model/question-type-master.model");
describe('Service Tests', function () {
    describe('QuestionTypeMaster Service', function () {
        var injector;
        var service;
        var httpMock;
        var elemDefault;
        var expectedResult;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [testing_2.HttpClientTestingModule],
            });
            expectedResult = null;
            injector = testing_1.getTestBed();
            service = injector.get(question_type_master_service_1.QuestionTypeMasterService);
            httpMock = injector.get(testing_2.HttpTestingController);
            elemDefault = new question_type_master_model_1.QuestionTypeMaster(0, 'AAAAAAA');
        });
        describe('Service methods', function () {
            it('should find an element', function () {
                var returnedFromService = Object.assign({}, elemDefault);
                service.find(123).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'GET' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(elemDefault);
            });
            it('should create a QuestionTypeMaster', function () {
                var returnedFromService = Object.assign({
                    id: 0,
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.create(new question_type_master_model_1.QuestionTypeMaster()).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'POST' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });
            it('should update a QuestionTypeMaster', function () {
                var returnedFromService = Object.assign({
                    name: 'BBBBBB',
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.update(expected).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'PUT' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });
            it('should return a list of QuestionTypeMaster', function () {
                var returnedFromService = Object.assign({
                    name: 'BBBBBB',
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.query().subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'GET' });
                req.flush([returnedFromService]);
                httpMock.verify();
                expect(expectedResult).toContainEqual(expected);
            });
            it('should delete a QuestionTypeMaster', function () {
                service["delete"](123).subscribe(function (resp) { return (expectedResult = resp.ok); });
                var req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
                expect(expectedResult);
            });
        });
        afterEach(function () {
            httpMock.verify();
        });
    });
});
//# sourceMappingURL=question-type-master.service.spec.js.map