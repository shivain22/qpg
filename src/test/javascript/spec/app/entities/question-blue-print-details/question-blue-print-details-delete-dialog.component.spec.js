"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var ng_bootstrap_1 = require("@ng-bootstrap/ng-bootstrap");
var rxjs_1 = require("rxjs");
var ng_jhipster_1 = require("ng-jhipster");
var test_module_1 = require("../../../test.module");
var question_blue_print_details_delete_dialog_component_1 = require("app/entities/question-blue-print-details/question-blue-print-details-delete-dialog.component");
var question_blue_print_details_service_1 = require("app/entities/question-blue-print-details/question-blue-print-details.service");
describe('Component Tests', function () {
    describe('QuestionBluePrintDetail Management Delete Component', function () {
        var comp;
        var fixture;
        var service;
        var mockEventManager;
        var mockActiveModal;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [question_blue_print_details_delete_dialog_component_1.QuestionBluePrintDetailsDeleteDialogComponent],
            })
                .overrideTemplate(question_blue_print_details_delete_dialog_component_1.QuestionBluePrintDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(question_blue_print_details_delete_dialog_component_1.QuestionBluePrintDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(question_blue_print_details_service_1.QuestionBluePrintDetailsService);
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
//# sourceMappingURL=question-blue-print-details-delete-dialog.component.spec.js.map