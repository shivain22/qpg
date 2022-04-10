"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var router_1 = require("@angular/router");
var rxjs_1 = require("rxjs");
var test_module_1 = require("../../../test.module");
var question_blue_print_details_detail_component_1 = require("app/entities/question-blue-print-details/question-blue-print-details-detail.component");
var question_blue_print_details_model_1 = require("app/shared/model/question-blue-print-details.model");
describe('Component Tests', function () {
    describe('QuestionBluePrintDetail Management Detail Component', function () {
        var comp;
        var fixture;
        var route = { data: rxjs_1.of({ questionBluePrintDetails: new question_blue_print_details_model_1.QuestionBluePrintDetails(123) }) };
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [question_blue_print_details_detail_component_1.QuestionBluePrintDetailsDetailComponent],
                providers: [{ provide: router_1.ActivatedRoute, useValue: route }],
            })
                .overrideTemplate(question_blue_print_details_detail_component_1.QuestionBluePrintDetailsDetailComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(question_blue_print_details_detail_component_1.QuestionBluePrintDetailsDetailComponent);
            comp = fixture.componentInstance;
        });
        describe('OnInit', function () {
            it('Should load questionBluePrintDetails on init', function () {
                // WHEN
                comp.ngOnInit();
                // THEN
                expect(comp.questionBluePrintDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
//# sourceMappingURL=question-blue-print-details-detail.component.spec.js.map