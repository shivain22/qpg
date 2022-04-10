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
exports.QuestionTypeMasterUpdateComponent = void 0;
var core_1 = require("@angular/core");
// eslint-disable-next-line @typescript-eslint/no-unused-vars
var forms_1 = require("@angular/forms");
var question_type_master_model_1 = require("app/shared/model/question-type-master.model");
var QuestionTypeCategoryMasterUpdateComponent = /** @class */ (function () {
    function QuestionTypeMasterUpdateComponent(questionTypeMasterService, activatedRoute, fb) {
        this.questionTypeMasterService = questionTypeMasterService;
        this.activatedRoute = activatedRoute;
        this.fb = fb;
        this.isSaving = false;
        this.editForm = this.fb.group({
            id: [],
            name: [null, [forms_1.Validators.required, forms_1.Validators.minLength(5), forms_1.Validators.maxLength(50)]],
            shortName: [null, [forms_1.Validators.required, forms_1.Validators.minLength(5), forms_1.Validators.maxLength(50)]],
        });
    }
    QuestionTypeMasterUpdateComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.activatedRoute.data.subscribe(function (_a) {
            var questionTypeMaster = _a.questionTypeMaster;
            _this.updateForm(questionTypeMaster);
        });
    };
    QuestionTypeMasterUpdateComponent.prototype.updateForm = function (questionTypeMaster) {
        this.editForm.patchValue({
            id: questionTypeMaster.id,
            name: questionTypeMaster.name,
            shortName: questionTypeMaster.shortName,
        });
    };
    QuestionTypeMasterUpdateComponent.prototype.previousState = function () {
        window.history.back();
    };
    QuestionTypeMasterUpdateComponent.prototype.save = function () {
        this.isSaving = true;
        var questionTypeMaster = this.createFromForm();
        if (questionTypeMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.questionTypeMasterService.update(questionTypeMaster));
        }
        else {
            this.subscribeToSaveResponse(this.questionTypeMasterService.create(questionTypeMaster));
        }
    };
    QuestionTypeMasterUpdateComponent.prototype.createFromForm = function () {
        return __assign(__assign({}, new question_type_master_model_1.QuestionTypeMaster()), { id: this.editForm.get(['id']).value, name: this.editForm.get(['name']).value, shortName: this.editForm.get(['shortName']).value });
    };
    QuestionTypeMasterUpdateComponent.prototype.subscribeToSaveResponse = function (result) {
        var _this = this;
        result.subscribe(function () { return _this.onSaveSuccess(); }, function () { return _this.onSaveError(); });
    };
    QuestionTypeMasterUpdateComponent.prototype.onSaveSuccess = function () {
        this.isSaving = false;
        this.previousState();
    };
    QuestionTypeMasterUpdateComponent.prototype.onSaveError = function () {
        this.isSaving = false;
    };
    QuestionTypeMasterUpdateComponent = __decorate([
        core_1.Component({
            selector: 'jhi-question-type-master-update',
            templateUrl: './question-type-category-master-update.component.html',
        })
    ], QuestionTypeMasterUpdateComponent);
    return QuestionTypeMasterUpdateComponent;
}());
exports.QuestionTypeMasterUpdateComponent = QuestionTypeCategoryMasterUpdateComponent;
//# sourceMappingURL=question-type-master-update.component.js.map
