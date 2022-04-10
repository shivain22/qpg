"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var difficulty_type_master_detail_component_1 = require("app/entities/difficulty-type-master/difficulty-type-master-detail.component");
var difficulty_type_master_model_1 = require("app/shared/model/difficulty-type-master.model");
describe('Component Tests', function () {
    describe('DifficultyTypeMaster Management Detail Component', function () {
        var comp;
        var fixture;
        var route = { data: rxjs_1.of({ difficultyTypeMaster: new difficulty_type_master_model_1.DifficultyTypeMaster(123) }) };
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [difficulty_type_master_detail_component_1.DifficultyTypeMasterDetailComponent],
                providers: [{ provide: router_1.ActivatedRoute, useValue: route }],
            })
                .overrideTemplate(difficulty_type_master_detail_component_1.DifficultyTypeMasterDetailComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(difficulty_type_master_detail_component_1.DifficultyTypeMasterDetailComponent);
            comp = fixture.componentInstance;
        });
        describe('OnInit', function () {
            it('Should load difficultyTypeMaster on init', function () {
                // WHEN
                comp.ngOnInit();
                // THEN
                expect(comp.difficultyTypeMaster).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
//# sourceMappingURL=difficulty-type-master-detail.component.spec.js.map