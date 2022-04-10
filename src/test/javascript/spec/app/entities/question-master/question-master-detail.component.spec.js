"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var question_master_detail_component_1 = require("app/entities/question-master/question-master-detail.component");
var question_master_model_1 = require("app/shared/model/question-master.model");
describe('Component Tests', function () {
    describe('QuestionMaster Management Detail Component', function () {
        var comp;
        var fixture;
        var route = { data: rxjs_1.of({ questionMaster: new question_master_model_1.QuestionMaster(123) }) };
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [question_master_detail_component_1.QuestionMasterDetailComponent],
                providers: [{ provide: router_1.ActivatedRoute, useValue: route }],
            })
                .overrideTemplate(question_master_detail_component_1.QuestionMasterDetailComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(question_master_detail_component_1.QuestionMasterDetailComponent);
            comp = fixture.componentInstance;
        });
        describe('OnInit', function () {
            it('Should load questionMaster on init', function () {
                // WHEN
                comp.ngOnInit();
                // THEN
                expect(comp.questionMaster).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
//# sourceMappingURL=question-master-detail.component.spec.js.map