"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var rxjs_1 = require("rxjs");
var ng_jhipster_1 = require("ng-jhipster");
var test_module_1 = require("../../../test.module");
var qp_bank_upload_master_delete_dialog_component_1 = require("app/entities/qp-bank-upload-master/qp-bank-upload-master-delete-dialog.component");
var qp_bank_upload_master_service_1 = require("app/entities/qp-bank-upload-master/qp-bank-upload-master.service");
describe('Component Tests', function () {
    describe('QpBankUploadMaster Management Delete Component', function () {
        var comp;
        var fixture;
        var service;
        var mockEventManager;
        var mockActiveModal;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [qp_bank_upload_master_delete_dialog_component_1.QpBankUploadMasterDeleteDialogComponent],
            })
                .overrideTemplate(qp_bank_upload_master_delete_dialog_component_1.QpBankUploadMasterDeleteDialogComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(qp_bank_upload_master_delete_dialog_component_1.QpBankUploadMasterDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(qp_bank_upload_master_service_1.QpBankUploadMasterService);
            mockEventManager = testing_1.TestBed.get(ng_jhipster_1.JhiEventManager);
            mockActiveModal = testing_1.TestBed.get(ng_bootstrap_1.NgbActiveModal);
        });
        describe('confirmDelete', function () {
            it('Should call delete service on confirmDelete', testing_1.inject([], testing_1.fakeAsync(function () {
                // GIVEN
                spyOn(service, 'delete').and.returnValue(rxjs_1.of({}));
                // WHEN
                comp.confirmDelete(123);
                testing_1.tick();
                // THEN
                expect(service["delete"]).toHaveBeenCalledWith(123);
                expect(mockActiveModal.closeSpy).toHaveBeenCalled();
                expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
            })));
            it('Should not call delete service on clear', function () {
                // GIVEN
                spyOn(service, 'delete');
                // WHEN
                comp.cancel();
                // THEN
                expect(service["delete"]).not.toHaveBeenCalled();
                expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
            });
        });
    });
});
//# sourceMappingURL=qp-bank-upload-master-delete-dialog.component.spec.js.map