"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var http_1 = require("@angular/common/http");
var forms_1 = require("@angular/forms");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var question_type_master_update_component_1 = require("app/entities/question-type-master/question-type-master-update.component");
var question_type_master_service_1 = require("app/entities/question-type-master/question-type-master.service");
var question_type_master_model_1 = require("app/shared/model/question-type-master.model");
describe('Component Tests', function () {
    describe('QuestionTypeMaster Management Update Component', function () {
        var comp;
        var fixture;
        var service;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [question_type_master_update_component_1.QuestionTypeMasterUpdateComponent],
                providers: [forms_1.FormBuilder],
            })
                .overrideTemplate(question_type_master_update_component_1.QuestionTypeMasterUpdateComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(question_type_master_update_component_1.QuestionTypeMasterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(question_type_master_service_1.QuestionTypeMasterService);
        });
        describe('save', function () {
            it('Should call update service on save for existing entity', testing_1.fakeAsync(function () {
                // GIVEN
                var entity = new question_type_master_model_1.QuestionTypeMaster(123);
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
                var entity = new question_type_master_model_1.QuestionTypeMaster();
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
//# sourceMappingURL=question-type-master-update.component.spec.js.map