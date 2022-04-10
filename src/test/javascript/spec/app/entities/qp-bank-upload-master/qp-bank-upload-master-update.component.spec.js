"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var http_1 = require("@angular/common/http");
var forms_1 = require("@angular/forms");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var qp_bank_upload_master_update_component_1 = require("app/entities/qp-bank-upload-master/qp-bank-upload-master-update.component");
var qp_bank_upload_master_service_1 = require("app/entities/qp-bank-upload-master/qp-bank-upload-master.service");
var qp_bank_upload_master_model_1 = require("app/shared/model/qp-bank-upload-master.model");
describe('Component Tests', function () {
    describe('QpBankUploadMaster Management Update Component', function () {
        var comp;
        var fixture;
        var service;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [qp_bank_upload_master_update_component_1.QpBankUploadMasterUpdateComponent],
                providers: [forms_1.FormBuilder],
            })
                .overrideTemplate(qp_bank_upload_master_update_component_1.QpBankUploadMasterUpdateComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(qp_bank_upload_master_update_component_1.QpBankUploadMasterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(qp_bank_upload_master_service_1.QpBankUploadMasterService);
        });
        describe('save', function () {
            it('Should call update service on save for existing entity', testing_1.fakeAsync(function () {
                // GIVEN
                var entity = new qp_bank_upload_master_model_1.QpBankUploadMaster(123);
                spyOn(service, 'update').and.returnValue(rxjs_1.of(new http_1.HttpResponse({ body: entity })));
                comp.updateForm(entity);
                // WHEN
                comp.save();
                testing_1.tick(); // simulate async
                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
            it('Should call create service on save for new entity', testing_1.fakeAsync(function () {
                // GIVEN
                var entity = new qp_bank_upload_master_model_1.QpBankUploadMaster();
                spyOn(service, 'create').and.returnValue(rxjs_1.of(new http_1.HttpResponse({ body: entity })));
                comp.updateForm(entity);
                // WHEN
                comp.save();
                testing_1.tick(); // simulate async
                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
//# sourceMappingURL=qp-bank-upload-master-update.component.spec.js.map