"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.QuestionTypeMasterDeleteDialogComponent = void 0;
var core_1 = require("@angular/core");
var QuestionTypeCategoryMasterDeleteDialogComponent = /** @class */ (function () {
    function QuestionTypeMasterDeleteDialogComponent(questionTypeMasterService, activeModal, eventManager) {
        this.questionTypeMasterService = questionTypeMasterService;
        this.activeModal = activeModal;
        this.eventManager = eventManager;
    }
    QuestionTypeMasterDeleteDialogComponent.prototype.cancel = function () {
        this.activeModal.dismiss();
    };
    QuestionTypeMasterDeleteDialogComponent.prototype.confirmDelete = function (id) {
        var _this = this;
        this.questionTypeMasterService["delete"](id).subscribe(function () {
            _this.eventManager.broadcast('questionTypeMasterListModification');
            _this.activeModal.close();
        });
    };
    QuestionTypeMasterDeleteDialogComponent = __decorate([
        core_1.Component({
            templateUrl: './question-type-category-master-delete-dialog.component.html',
        })
    ], QuestionTypeMasterDeleteDialogComponent);
    return QuestionTypeMasterDeleteDialogComponent;
}());
exports.QuestionTypeMasterDeleteDialogComponent = QuestionTypeCategoryMasterDeleteDialogComponent;
//# sourceMappingURL=question-type-master-delete-dialog.component.js.map
