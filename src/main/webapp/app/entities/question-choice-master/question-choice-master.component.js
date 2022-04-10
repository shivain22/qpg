"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.QuestionChoiceMasterComponent = void 0;
var core_1 = require("@angular/core");
var rxjs_1 = require("rxjs");
var pagination_constants_1 = require("../../shared/constants/pagination.constants");
var QuestionChoiceMasterComponent = /** @class */ (function () {
    function QuestionChoiceMasterComponent(questionChoiceMasterService, activatedRoute, router, eventManager, modalService) {
        this.questionChoiceMasterService = questionChoiceMasterService;
        this.activatedRoute = activatedRoute;
        this.router = router;
        this.eventManager = eventManager;
        this.modalService = modalService;
        this.totalItems = 0;
        this.itemsPerPage = pagination_constants_1.ITEMS_PER_PAGE;
        this.ngbPaginationPage = 1;
    }
    QuestionChoiceMasterComponent.prototype.loadPage = function (page, dontNavigate) {
        var _this = this;
        var pageToLoad = page || this.page || 1;
        this.questionChoiceMasterService
            .query({
            page: pageToLoad - 1,
            size: this.itemsPerPage,
            sort: this.sort(),
        })
            .subscribe(function (res) { return _this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate); }, function () { return _this.onError(); });
    };
    QuestionChoiceMasterComponent.prototype.ngOnInit = function () {
        this.handleNavigation();
        this.registerChangeInQuestionChoiceMasters();
    };
    QuestionChoiceMasterComponent.prototype.handleNavigation = function () {
        var _this = this;
        rxjs_1.combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, function (data, params) {
            var _a;
            var page = params.get('page');
            var pageNumber = page !== null ? +page : 1;
            var sort = ((_a = params.get('sort')) !== null && _a !== void 0 ? _a : data['defaultSort']).split(',');
            var predicate = sort[0];
            var ascending = sort[1] === 'asc';
            if (pageNumber !== _this.page || predicate !== _this.predicate || ascending !== _this.ascending) {
                _this.predicate = predicate;
                _this.ascending = ascending;
                _this.loadPage(pageNumber, true);
            }
        }).subscribe();
    };
    QuestionChoiceMasterComponent.prototype.ngOnDestroy = function () {
        if (this.eventSubscriber) {
            this.eventManager.destroy(this.eventSubscriber);
        }
    };
    QuestionChoiceMasterComponent.prototype.trackId = function (index, item) {
        // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
        return item.id;
    };
    QuestionChoiceMasterComponent.prototype.registerChangeInQuestionChoiceMasters = function () {
        var _this = this;
        this.eventSubscriber = this.eventManager.subscribe('questionChoiceMasterListModification', function () { return _this.loadPage(); });
    };
    /* delete(questionChoiceMaster: IQuestionChoiceMaster): void {
       const modalRef = this.modalService.open(QuestionChoiceMasterDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
       modalRef.componentInstance.questionChoiceMaster = questionChoiceMaster;
     }*/
    QuestionChoiceMasterComponent.prototype.sort = function () {
        var result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    };
    QuestionChoiceMasterComponent.prototype.onSuccess = function (data, headers, page, navigate) {
        this.totalItems = Number(headers.get('X-Total-Count'));
        this.page = page;
        if (navigate) {
            this.router.navigate(['/question-type-master'], {
                queryParams: {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
                },
            });
        }
        this.questionChoiceMasters = data || [];
        this.ngbPaginationPage = this.page;
    };
    QuestionChoiceMasterComponent.prototype.onError = function () {
        var _a;
        this.ngbPaginationPage = (_a = this.page) !== null && _a !== void 0 ? _a : 1;
    };
    QuestionChoiceMasterComponent = __decorate([
        core_1.Component({
            selector: 'jhi-question-choice-master',
            templateUrl: './question-choice-master.component.html',
        })
    ], QuestionChoiceMasterComponent);
    return QuestionChoiceMasterComponent;
}());
exports.QuestionChoiceMasterComponent = QuestionChoiceMasterComponent;
//# sourceMappingURL=question-choice-master.component.js.map