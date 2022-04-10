"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var http_1 = require("@angular/common/http");
var forms_1 = require("@angular/forms");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var difficulty_type_master_update_component_1 = require("app/entities/difficulty-type-master/difficulty-type-master-update.component");
var difficulty_type_master_service_1 = require("app/entities/difficulty-type-master/difficulty-type-master.service");
var difficulty_type_master_model_1 = require("app/shared/model/difficulty-type-master.model");
describe('Component Tests', function () {
    describe('DifficultyTypeMaster Management Update Component', function () {
        var comp;
        var fixture;
        var service;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [difficulty_type_master_update_component_1.DifficultyTypeMasterUpdateComponent],
                providers: [forms_1.FormBuilder],
            })
                .overrideTemplate(difficulty_type_master_update_component_1.DifficultyTypeMasterUpdateComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(difficulty_type_master_update_component_1.DifficultyTypeMasterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(difficulty_type_master_service_1.DifficultyTypeMasterService);
        });
        describe('save', function () {
            it('Should call update service on save for existing entity', testing_1.fakeAsync(function () {
                // GIVEN
                var entity = new difficulty_type_master_model_1.DifficultyTypeMaster(123);
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
                var entity = new difficulty_type_master_model_1.DifficultyTypeMaster();
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
//# sourceMappingURL=difficulty-type-master-update.component.spec.js.map