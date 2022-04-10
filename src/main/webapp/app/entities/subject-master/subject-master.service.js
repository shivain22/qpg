"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.SubjectMasterService = void 0;
var core_1 = require("@angular/core");
var app_constants_1 = require("app/app.constants");
var request_util_1 = require("app/shared/util/request-util");
var SubjectMasterService = /** @class */ (function () {
    function SubjectMasterService(http) {
        this.http = http;
        this.resourceUrl = app_constants_1.SERVER_API_URL + 'api/subject-masters';
    }
    SubjectMasterService.prototype.create = function (subjectMaster) {
        return this.http.post(this.resourceUrl, subjectMaster, { observe: 'response' });
    };
    SubjectMasterService.prototype.update = function (subjectMaster) {
        return this.http.put(this.resourceUrl, subjectMaster, { observe: 'response' });
    };
    SubjectMasterService.prototype.find = function (id) {
        return this.http.get(this.resourceUrl + "/" + id, { observe: 'response' });
    };
    SubjectMasterService.prototype.query = function (req) {
        var options = request_util_1.createRequestOption(req);
        return this.http.get(this.resourceUrl, { params: options, observe: 'response' });
    };
    SubjectMasterService.prototype["delete"] = function (id) {
        return this.http["delete"](this.resourceUrl + "/" + id, { observe: 'response' });
    };
    SubjectMasterService = __decorate([
        core_1.Injectable({ providedIn: 'root' })
    ], SubjectMasterService);
    return SubjectMasterService;
}());
exports.SubjectMasterService = SubjectMasterService;
//# sourceMappingURL=subject-master.service.js.map