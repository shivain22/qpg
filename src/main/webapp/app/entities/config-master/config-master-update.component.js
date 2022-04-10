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
exports.ConfigMasterUpdateComponent = void 0;
var core_1 = require("@angular/core");
// eslint-disable-next-line @typescript-eslint/no-unused-vars
var forms_1 = require("@angular/forms");
var config_master_model_1 = require("../../shared/model/config-master.model");
var ConfigMasterUpdateComponent = /** @class */ (function () {
    function ConfigMasterUpdateComponent(configMasterService, activatedRoute, fb) {
        this.configMasterService = configMasterService;
        this.activatedRoute = activatedRoute;
        this.fb = fb;
        this.isSaving = false;
        this.editForm = this.fb.group({
            id: [],
            name: [null, [forms_1.Validators.required, forms_1.Validators.minLength(5), forms_1.Validators.maxLength(500)]],
        });
    }
    ConfigMasterUpdateComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.activatedRoute.data.subscribe(function (_a) {
            var configMaster = _a.configMaster;
            _this.updateForm(configMaster);
        });
    };
    ConfigMasterUpdateComponent.prototype.updateForm = function (configMaster) {
        this.editForm.patchValue({
            id: configMaster.id,
            name: configMaster.name,
        });
    };
    ConfigMasterUpdateComponent.prototype.previousState = function () {
        window.history.back();
    };
    ConfigMasterUpdateComponent.prototype.save = function () {
        this.isSaving = true;
        var configMaster = this.createFromForm();
        if (configMaster.id !== undefined) {
            this.subscribeToSaveResponse(this.configMasterService.update(configMaster));
        }
        else {
            this.subscribeToSaveResponse(this.configMasterService.create(configMaster));
        }
    };
    ConfigMasterUpdateComponent.prototype.createFromForm = function () {
        return __assign(__assign({}, new config_master_model_1.ConfigMaster()), { id: this.editForm.get(['id']).value, name: this.editForm.get(['name']).value });
    };
    ConfigMasterUpdateComponent.prototype.subscribeToSaveResponse = function (result) {
        var _this = this;
        result.subscribe(function () { return _this.onSaveSuccess(); }, function () { return _this.onSaveError(); });
    };
    ConfigMasterUpdateComponent.prototype.onSaveSuccess = function () {
        this.isSaving = false;
        this.previousState();
    };
    ConfigMasterUpdateComponent.prototype.onSaveError = function () {
        this.isSaving = false;
    };
    ConfigMasterUpdateComponent = __decorate([
        core_1.Component({
            selector: 'jhi-conifig-master-update',
            templateUrl: './config-master-update.component.html',
        })
    ], ConfigMasterUpdateComponent);
    return ConfigMasterUpdateComponent;
}());
exports.ConfigMasterUpdateComponent = ConfigMasterUpdateComponent;
//# sourceMappingURL=config-master-update.component.js.map