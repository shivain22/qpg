"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var testing_2 = require("@angular/common/http/testing");
var qp_bank_upload_master_service_1 = require("app/entities/qp-bank-upload-master/qp-bank-upload-master.service");
var qp_bank_upload_master_model_1 = require("app/shared/model/qp-bank-upload-master.model");
describe('Service Tests', function () {
    describe('QpBankUploadMaster Service', function () {
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
            service = injector.get(qp_bank_upload_master_service_1.QpBankUploadMasterService);
            httpMock = injector.get(testing_2.HttpTestingController);
            elemDefault = new qp_bank_upload_master_model_1.QpBankUploadMaster(0, 'image/png', 'AAAAAAA', 'AAAAAAA');
        });
        describe('Service methods', function () {
            it('should find an element', function () {
                var returnedFromService = Object.assign({}, elemDefault);
                service.find(123).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'GET' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(elemDefault);
            });
            it('should create a QpBankUploadMaster', function () {
                var returnedFromService = Object.assign({
                    id: 0,
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.create(new qp_bank_upload_master_model_1.QpBankUploadMaster()).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'POST' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });
            it('should update a QpBankUploadMaster', function () {
                var returnedFromService = Object.assign({
                    qpBankFile: 'BBBBBB',
                    name: 'BBBBBB',
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.update(expected).subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'PUT' });
                req.flush(returnedFromService);
                expect(expectedResult).toMatchObject(expected);
            });
            it('should return a list of QpBankUploadMaster', function () {
                var returnedFromService = Object.assign({
                    qpBankFile: 'BBBBBB',
                    name: 'BBBBBB',
                }, elemDefault);
                var expected = Object.assign({}, returnedFromService);
                service.query().subscribe(function (resp) { return (expectedResult = resp.body); });
                var req = httpMock.expectOne({ method: 'GET' });
                req.flush([returnedFromService]);
                httpMock.verify();
                expect(expectedResult).toContainEqual(expected);
            });
            it('should delete a QpBankUploadMaster', function () {
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
//# sourceMappingURL=qp-bank-upload-master.service.spec.js.map