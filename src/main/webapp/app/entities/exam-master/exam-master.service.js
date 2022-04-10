"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.ExamMasterService = void 0;
var core_1 = require("@angular/core");
var operators_1 = require("rxjs/operators");
var moment = require("moment");
var input_constants_1 = require("app/shared/constants/input.constants");
var app_constants_1 = require("app/app.constants");
var request_util_1 = require("app/shared/util/request-util");
var ExamMasterService = /** @class */ (function () {
    function ExamMasterService(http) {
        this.http = http;
        this.resourceUrl = app_constants_1.SERVER_API_URL + 'api/exam-masters';
    }
    ExamMasterService.prototype.create = function (examMaster) {
        var _this = this;
        var copy = this.convertDateFromClient(examMaster);
        return this.http
            .post(this.resourceUrl, copy, { observe: 'response' })
            .pipe(operators_1.map(function (res) { return _this.convertDateFromServer(res); }));
    };
    ExamMasterService.prototype.update = function (examMaster) {
        var _this = this;
        var copy = this.convertDateFromClient(examMaster);
        return this.http
            .put(this.resourceUrl, copy, { observe: 'response' })
            .pipe(operators_1.map(function (res) { return _this.convertDateFromServer(res); }));
    };
    ExamMasterService.prototype.find = function (id) {
        var _this = this;
        return this.http
            .get(this.resourceUrl + "/" + id, { observe: 'response' })
            .pipe(operators_1.map(function (res) { return _this.convertDateFromServer(res); }));
    };
    ExamMasterService.prototype.query = function (req) {
        var _this = this;
        var options = request_util_1.createRequestOption(req);
        return this.http
            .get(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(operators_1.map(function (res) { return _this.convertDateArrayFromServer(res); }));
    };
    ExamMasterService.prototype["delete"] = function (id) {
        return this.http["delete"](this.resourceUrl + "/" + id, { observe: 'response' });
    };
    ExamMasterService.prototype.convertDateFromClient = function (examMaster) {
        var copy = Object.assign({}, examMaster, {
            startDate: examMaster.startDate && examMaster.startDate.isValid() ? examMaster.startDate.format(input_constants_1.DATE_FORMAT) : undefined,
        });
        return copy;
    };
    ExamMasterService.prototype.convertDateFromServer = function (res) {
        if (res.body) {
            res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
        }
        return res;
    };
    ExamMasterService.prototype.convertDateArrayFromServer = function (res) {
        if (res.body) {
            res.body.forEach(function (examMaster) {
                examMaster.startDate = examMaster.startDate ? moment(examMaster.startDate) : undefined;
            });
        }
        return res;
    };
    ExamMasterService = __decorate([
        core_1.Injectable({ providedIn: 'root' })
    ], ExamMasterService);
    return ExamMasterService;
}());
exports.ExamMasterService = ExamMasterService;
//# sourceMappingURL=exam-master.service.js.map