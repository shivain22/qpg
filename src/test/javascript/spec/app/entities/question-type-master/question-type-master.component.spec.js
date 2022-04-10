"use strict";
exports.__esModule = true;
var testing_1 = require("@angular/core/testing");
var rxjs_1 = require("rxjs");
var http_1 = require("@angular/common/http");
var router_1 = require("@angular/router");
var test_module_1 = require("../../../test.module");
var question_type_master_component_1 = require("app/entities/question-type-master/question-type-master.component");
var question_type_master_service_1 = require("app/entities/question-type-master/question-type-master.service");
var question_type_master_model_1 = require("app/shared/model/question-type-master.model");
describe('Component Tests', function () {
    describe('QuestionTypeMaster Management Component', function () {
        var comp;
        var fixture;
        var service;
        beforeEach(function () {
            testing_1.TestBed.configureTestingModule({
                imports: [test_module_1.QpgTestModule],
                declarations: [question_type_master_component_1.QuestionTypeMasterComponent],
                providers: [
                    {
                        provide: router_1.ActivatedRoute,
                        useValue: {
                            data: rxjs_1.of({
                                defaultSort: 'id,asc',
                            }),
                            queryParamMap: rxjs_1.of(router_1.convertToParamMap({
                                page: '1',
                                size: '1',
                                sort: 'id,desc',
                            })),
                        },
                    },
                ],
            })
                .overrideTemplate(question_type_master_component_1.QuestionTypeMasterComponent, '')
                .compileComponents();
            fixture = testing_1.TestBed.createComponent(question_type_master_component_1.QuestionTypeMasterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(question_type_master_service_1.QuestionTypeMasterService);
        });
        it('Should call load all on init', function () {
            // GIVEN
            var headers = new http_1.HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(rxjs_1.of(new http_1.HttpResponse({
                body: [new question_type_master_model_1.QuestionTypeMaster(123)],
                headers: headers,
            })));
            // WHEN
            comp.ngOnInit();
            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.questionTypeMasters && comp.questionTypeMasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
        it('should load a page', function () {
            // GIVEN
            var headers = new http_1.HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(rxjs_1.of(new http_1.HttpResponse({
                body: [new question_type_master_model_1.QuestionTypeMaster(123)],
                headers: headers,
            })));
            // WHEN
            comp.loadPage(1);
            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.questionTypeMasters && comp.questionTypeMasters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
        it('should calculate the sort attribute for an id', function () {
            // WHEN
            comp.ngOnInit();
            var result = comp.sort();
            // THEN
            expect(result).toEqual(['id,desc']);
        });
        it('should calculate the sort attribute for a non-id attribute', function () {
            // INIT
            comp.ngOnInit();
            // GIVEN
            comp.predicate = 'name';
            // WHEN
            var result = comp.sort();
            // THEN
            expect(result).toEqual(['name,desc', 'id']);
        });
    });
});
//# sourceMappingURL=question-type-master.component.spec.js.map