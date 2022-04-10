"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.ExamMasterCreateQuestionPaperDetailComponent = void 0;
var core_1 = require("@angular/core");
var exam_master_model_1 = require("app/shared/model/exam-master.model");
var forms_1 = require("@angular/forms");
var ExamMasterCreateQuestionPaperDetailComponent = /** @class */ (function () {
    function ExamMasterCreateQuestionPaperDetailComponent(activatedRoute, examMasterService, fb, questionBluePrintMasterService, collegeMasterService, questionBankMasterService, departmentMasterService, courseMasterService, categoryMasterService, subCategoryMasterService, subjectMasterService, subSubjectMasterService, topicMasterService, subTopicMasterService) {
        this.activatedRoute = activatedRoute;
        this.examMasterService = examMasterService;
        this.fb = fb;
        this.questionBluePrintMasterService = questionBluePrintMasterService;
        this.collegeMasterService = collegeMasterService;
        this.questionBankMasterService = questionBankMasterService;
        this.departmentMasterService = departmentMasterService;
        this.courseMasterService = courseMasterService;
        this.categoryMasterService = categoryMasterService;
        this.subCategoryMasterService = subCategoryMasterService;
        this.subjectMasterService = subjectMasterService;
        this.subSubjectMasterService = subSubjectMasterService;
        this.topicMasterService = topicMasterService;
        this.subTopicMasterService = subTopicMasterService;
        this.examMaster = null;
        this.isSaving = false;
        this.questionBluePrintMasters = [];
        this.collegemasters = [];
        this.departmentmasters = [];
        this.coursemasters = [];
        this.categorymasters = [];
        this.subcategorymasters = [];
        this.subjectmasters = [];
        this.subsubjectmasters = [];
        this.topicmasters = [];
        this.subtopicmasters = [];
        this.filteredcollegemasters = [];
        this.filtereddepartmentmasters = [];
        this.filteredcoursemasters = [];
        this.filteredcategorymasters = [];
        this.filteredsubcategorymasters = [];
        this.filteredsubjectmasters = [];
        this.filteredsubsubjectmasters = [];
        this.filteredtopicmasters = [];
        this.filteredsubtopicmasters = [];
        this.editForm = this.fb.group({
            id: [],
            title: [null, [forms_1.Validators.required, forms_1.Validators.minLength(5), forms_1.Validators.maxLength(100)]],
            startDate: [],
            collegeMaster: [null, forms_1.Validators.required],
            departmentMaster: [null, forms_1.Validators.required],
            courseMaster: [null, forms_1.Validators.required],
            categoryMaster: [null, forms_1.Validators.required],
            subCategoryMaster: [null, forms_1.Validators.required],
            subjectMaster: [null, forms_1.Validators.required],
            subSubjectMaster: [null, forms_1.Validators.required],
            topicMaster: [null, forms_1.Validators.required],
            subTopicMaster: [],
            questionBluePrintMaster: [null, forms_1.Validators.required],
        });
    }
    ExamMasterCreateQuestionPaperDetailComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.activatedRoute.data.subscribe(function (_a) {
            var examMaster = _a.examMaster;
            _this.examMaster = examMaster;
            _this.updateForm(examMaster);
            _this.questionBluePrintMasterService
                .query()
                .subscribe(function (res) { return (_this.questionBluePrintMasters = res.body || []); });
            _this.collegeMasterService.query().subscribe(function (res) { return (_this.collegemasters = res.body || []); });
            _this.departmentMasterService.query().subscribe(function (res) { return (_this.departmentmasters = res.body || []); });
            _this.courseMasterService.query().subscribe(function (res) { return (_this.coursemasters = res.body || []); });
            _this.categoryMasterService.query().subscribe(function (res) { return (_this.categorymasters = res.body || []); });
            _this.subCategoryMasterService
                .query()
                .subscribe(function (res) { return (_this.subcategorymasters = res.body || []); });
            _this.subjectMasterService.query().subscribe(function (res) { return (_this.subjectmasters = res.body || []); });
            _this.subSubjectMasterService.query().subscribe(function (res) { return (_this.subsubjectmasters = res.body || []); });
            _this.topicMasterService.query().subscribe(function (res) { return (_this.topicmasters = res.body || []); });
            _this.subTopicMasterService.query().subscribe(function (res) { return (_this.subtopicmasters = res.body || []); });
        });
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.previousState = function () {
        window.history.back();
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.updateForm = function (examMaster) {
        this.editForm.patchValue({
            id: examMaster.id,
            title: examMaster.title,
            startDate: examMaster.startDate,
            collegeMaster: examMaster.collegeMaster,
            departmentMaster: examMaster.departmentMaster,
            courseMaster: examMaster.courseMaster,
            categoryMaster: examMaster.categoryMaster,
            subCategoryMaster: examMaster.subCategoryMaster,
            subjectMaster: examMaster.subjectMaster,
            subSubjectMaster: examMaster.subSubjectMaster,
            topicMaster: examMaster.topicMaster,
            subTopicMaster: examMaster.subTopicMaster,
            questionBluePrintMaster: examMaster.questionBluePrintMaster,
        });
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.createFromForm = function () {
        /* eslint-disable no-console */
        console.log(this.editForm.get(['topicMaster']).value);
        /* eslint-enable no-console */
        return __assign(__assign({}, new exam_master_model_1.ExamMaster()), { id: this.editForm.get(['id']).value, title: this.editForm.get(['title']).value, startDate: this.editForm.get(['startDate']).value, collegeMaster: this.editForm.get(['collegeMaster']).value, departmentMaster: this.editForm.get(['departmentMaster']).value, courseMaster: this.editForm.get(['courseMaster']).value, categoryMaster: this.editForm.get(['categoryMaster']).value, subCategoryMaster: this.editForm.get(['subCategoryMaster']).value, subjectMaster: this.editForm.get(['subjectMaster']).value, subSubjectMaster: this.editForm.get(['subSubjectMaster']).value, topicMaster: this.editForm.get(['topicMaster']).value, subTopicMaster: this.editForm.get(['subTopicMaster']).value, questionBluePrintMaster: this.editForm.get(['questionBluePrintMaster']).value });
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.save = function () {
        this.isSaving = true;
        var examMaster = this.createFromForm();
        if (examMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.examMasterService.generateQuestionPaper(examMaster));
        }
        else {
            this.subscribeToSaveResponse(this.examMasterService.create(examMaster));
        }
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.subscribeToSaveResponse = function (result) {
        var _this = this;
        result.subscribe(function () { return _this.onSaveSuccess(); }, function () { return _this.onSaveError(); });
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.onSaveSuccess = function () {
        this.isSaving = false;
        this.previousState();
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.onSaveError = function () {
        this.isSaving = false;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackById = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackByCollegeId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackByDeptId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackByCourseId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackByCategoryId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackBySubCategoryId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackBySubjectId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackBySubSubjectId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackByTopicId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent.prototype.trackBySubTopicId = function (index, item) {
        return item.id;
    };
    ExamMasterCreateQuestionPaperDetailComponent = __decorate([
        core_1.Component({
            selector: 'jhi-exam-master-detail',
            templateUrl: './exam-master-create-question-paper-detail.component.html',
        })
    ], ExamMasterCreateQuestionPaperDetailComponent);
    return ExamMasterCreateQuestionPaperDetailComponent;
}());
exports.ExamMasterCreateQuestionPaperDetailComponent = ExamMasterCreateQuestionPaperDetailComponent;
//# sourceMappingURL=exam-master-create-question-paper-detail.component.js.map