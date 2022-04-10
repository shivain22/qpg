"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var ng_jhipster_1 = require("ng-jhipster");
var test_module_1 = require("../../../test.module");
var qp_bank_upload_master_detail_component_1 = require("app/entities/qp-bank-upload-master/qp-bank-upload-master-detail.component");
var qp_bank_upload_master_model_1 = require("app/shared/model/qp-bank-upload-master.model");
describe('Component Tests', function () {
    describe('QpBankUploadMaster Management Detail Component', function () {
        var comp;
        var fixture;
        var dataUtils;
        var route = { data: rxjs_1.of({ qpBankUploadMaster: new qp_bank_upload_master_model_1.QpBankUploadMaster(123) }) };
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [qp_bank_upload_master_detail_component_1.QpBankUploadMasterDetailComponent],
                providers: [{ provide: router_1.ActivatedRoute, useValue: route }],
            })
                .overrideTemplate(qp_bank_upload_master_detail_component_1.QpBankUploadMasterDetailComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(qp_bank_upload_master_detail_component_1.QpBankUploadMasterDetailComponent);
            comp = fixture.componentInstance;
            dataUtils = fixture.debugElement.injector.get(ng_jhipster_1.JhiDataUtils);
        });
        describe('OnInit', function () {
            it('Should load qpBankUploadMaster on init', function () {
                // WHEN
                comp.ngOnInit();
                // THEN
                expect(comp.qpBankUploadMaster).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
        describe('byteSize', function () {
            it('Should call byteSize from JhiDataUtils', function () {
                // GIVEN
                spyOn(dataUtils, 'byteSize');
                var fakeBase64 = 'fake base64';
                // WHEN
                comp.byteSize(fakeBase64);
                // THEN
                expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
            });
        });
        describe('openFile', function () {
            it('Should call openFile from JhiDataUtils', function () {
                // GIVEN
                spyOn(dataUtils, 'openFile');
                var fakeContentType = 'fake content type';
                var fakeBase64 = 'fake base64';
                // WHEN
                comp.openFile(fakeContentType, fakeBase64);
                // THEN
                expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
            });
        });
    });
});
//# sourceMappingURL=qp-bank-upload-master-detail.component.spec.js.map