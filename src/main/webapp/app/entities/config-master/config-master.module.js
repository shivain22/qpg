"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.QpgConfigMasterModule = void 0;
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var shared_module_1 = require("app/shared/shared.module");
var config_master_component_1 = require("./config-master.component");
var config_master_detail_component_1 = require("./config-master-detail.component");
var config_master_update_component_1 = require("./config-master-update.component");
var config_master_delete_dialog_component_1 = require("./config-master-delete-dialog.component");
var config_master_route_1 = require("./config-master.route");
var QpgConfigMasterModule = /** @class */ (function () {
    function QpgConfigMasterModule() {
    }
    QpgConfigMasterModule = __decorate([
        core_1.NgModule({
            imports: [shared_module_1.QpgSharedModule, router_1.RouterModule.forChild(config_master_route_1.configMasterRoute)],
            declarations: [config_master_component_1.ConfigMasterComponent, config_master_detail_component_1.ConfigMasterDetailComponent, config_master_update_component_1.ConfigMasterUpdateComponent, config_master_delete_dialog_component_1.ConfigMasterDeleteDialogComponent],
            entryComponents: [config_master_delete_dialog_component_1.ConfigMasterDeleteDialogComponent],
        })
    ], QpgConfigMasterModule);
    return QpgConfigMasterModule;
}());
exports.QpgConfigMasterModule = QpgConfigMasterModule;
//# sourceMappingURL=config-master.module.js.map