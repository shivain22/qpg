"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.SubSubjectMasterService = void 0;
var core_1 = require("@angular/core");
var app_constants_1 = require("app/app.constants");
var request_util_1 = require("app/shared/util/request-util");
var SubSubjectMasterService = /** @class */ (function () {
    function SubSubjectMasterService(http) {
        this.http = http;
        this.resourceUrl = app_constants_1.SERVER_API_URL + 'api/sub-subject-masters';
    }
    SubSubjectMasterService.prototype.create = function (subSubjectMaster) {
        return this.http.post(this.resourceUrl, subSubjectMaster, { observe: 'response' });
    };
    SubSubjectMasterService.prototype.update = function (subSubjectMaster) {
        return this.http.put(this.resourceUrl, subSubjectMaster, { observe: 'response' });
    };
    SubSubjectMasterService.prototype.find = function (id) {
        return this.http.get(this.resourceUrl + "/" + id, { observe: 'response' });
    };
    SubSubjectMasterService.prototype.query = function (req) {
        var options = request_util_1.createRequestOption(req);
        return this.http.get(this.resourceUrl, { params: options, observe: 'response' });
    };
    SubSubjectMasterService.prototype["delete"] = function (id) {
        return this.http["delete"](this.resourceUrl + "/" + id, { observe: 'response' });
    };
    SubSubjectMasterService = __decorate([
        core_1.Injectable({ providedIn: 'root' })
    ], SubSubjectMasterService);
    return SubSubjectMasterService;
}());
exports.SubSubjectMasterService = SubSubjectMasterService;
//# sourceMappingURL=sub-subject-master.service.js.map