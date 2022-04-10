"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.ConfigMasterDeleteDialogComponent = void 0;
var core_1 = require("@angular/core");
var ConfigMasterDeleteDialogComponent = /** @class */ (function () {
    function ConfigMasterDeleteDialogComponent(configMasterService, activeModal, eventManager) {
        this.configMasterService = configMasterService;
        this.activeModal = activeModal;
        this.eventManager = eventManager;
    }
    ConfigMasterDeleteDialogComponent.prototype.cancel = function () {
        this.activeModal.dismiss();
    };
    ConfigMasterDeleteDialogComponent.prototype.confirmDelete = function (id) {
        var _this = this;
        this.configMasterService["delete"](id).subscribe(function () {
            _this.eventManager.broadcast('configMasterListModification');
            _this.activeModal.close();
        });
    };
    ConfigMasterDeleteDialogComponent = __decorate([
        core_1.Component({
            templateUrl: './config-master-delete-dialog.component.html',
        })
    ], ConfigMasterDeleteDialogComponent);
    return ConfigMasterDeleteDialogComponent;
}());
exports.ConfigMasterDeleteDialogComponent = ConfigMasterDeleteDialogComponent;
//# sourceMappingURL=config-master-delete-dialog.component.js.map