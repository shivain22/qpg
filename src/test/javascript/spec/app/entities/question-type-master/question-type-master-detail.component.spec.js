"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var question_type_master_detail_component_1 = require("app/entities/question-type-master/question-type-master-detail.component");
var question_type_master_model_1 = require("app/shared/model/question-type-master.model");
describe('Component Tests', function () {
    describe('QuestionTypeMaster Management Detail Component', function () {
        var comp;
        var fixture;
        var route = { data: rxjs_1.of({ questionTypeMaster: new question_type_master_model_1.QuestionTypeMaster(123) }) };
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [question_type_master_detail_component_1.QuestionTypeMasterDetailComponent],
                providers: [{ provide: router_1.ActivatedRoute, useValue: route }],
            })
                .overrideTemplate(question_type_master_detail_component_1.QuestionTypeMasterDetailComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(question_type_master_detail_component_1.QuestionTypeMasterDetailComponent);
            comp = fixture.componentInstance;
        });
        describe('OnInit', function () {
            it('Should load questionTypeMaster on init', function () {
                // WHEN
                comp.ngOnInit();
                // THEN
                expect(comp.questionTypeMaster).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
//# sourceMappingURL=question-type-master-detail.component.spec.js.map