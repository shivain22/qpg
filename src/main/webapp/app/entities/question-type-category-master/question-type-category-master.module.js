"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.QpgQuestionTypeMasterModule = void 0;
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var shared_module_1 = require("app/shared/shared.module");
var question_type_category_master_component_1 = require("./question-type-category-master.component");
var question_type_master_detail_component_1 = require("./question-type-category-master-detail.component");
var question_type_master_update_component_1 = require("./question-type-category-master-update.component");
var question_type_master_delete_dialog_component_1 = require("./question-type-category-master-delete-dialog.component");
var question_type_master_route_1 = require("./question-type-category-master.route");
var QpgQuestionTypeMasterModule = /** @class */ (function () {
    function QpgQuestionTypeMasterModule() {
    }
    QpgQuestionTypeMasterModule = __decorate([
        core_1.NgModule({
            imports: [shared_module_1.QpgSharedModule, router_1.RouterModule.forChild(question_type_master_route_1.questionTypeCategoryMasterRoute)],
            declarations: [
                question_type_category_master_component_1.QuestionTypeCategoryMasterComponent,
                question_type_master_detail_component_1.QuestionTypeCategoryMasterDetailComponent,
                question_type_master_update_component_1.QuestionTypeCategoryMasterUpdateComponent,
                question_type_master_delete_dialog_component_1.QuestionTypeCategoryMasterDeleteDialogComponent,
            ],
            entryComponents: [question_type_master_delete_dialog_component_1.QuestionTypeCategoryMasterDeleteDialogComponent],
        })
    ], QpgQuestionTypeMasterModule);
    return QpgQuestionTypeMasterModule;
}());
exports.QpgQuestionTypeMasterModule = QpgQuestionTypeMasterModule;
//# sourceMappingURL=question-type-master.module.js.map
