"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var answer_master_detail_component_1 = require("app/entities/answer-master/answer-master-detail.component");
var answer_master_model_1 = require("app/shared/model/answer-master.model");
describe('Component Tests', function () {
    describe('AnswerMaster Management Detail Component', function () {
        var comp;
        var fixture;
        var route = { data: rxjs_1.of({ answerMaster: new answer_master_model_1.AnswerMaster(123) }) };
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [answer_master_detail_component_1.AnswerMasterDetailComponent],
                providers: [{ provide: router_1.ActivatedRoute, useValue: route }],
            })
                .overrideTemplate(answer_master_detail_component_1.AnswerMasterDetailComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(answer_master_detail_component_1.AnswerMasterDetailComponent);
            comp = fixture.componentInstance;
        });
        describe('OnInit', function () {
            it('Should load answerMaster on init', function () {
                // WHEN
                comp.ngOnInit();
                // THEN
                expect(comp.answerMaster).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
//# sourceMappingURL=answer-master-detail.component.spec.js.map