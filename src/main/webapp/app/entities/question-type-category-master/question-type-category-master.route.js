"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.questionTypeMasterRoute = exports.QuestionTypeMasterResolve = void 0;
var core_1 = require("@angular/core");
var rxjs_1 = require("rxjs");
var operators_1 = require("rxjs/operators");
var authority_constants_1 = require("app/shared/constants/authority.constants");
var user_route_access_service_1 = require("app/core/auth/user-route-access-service");
var question_type_master_model_1 = require("app/shared/model/question-type-master.model");
var question_type_category_master_component_1 = require("./question-type-category-master.component");
var question_type_master_detail_component_1 = require("./question-type-master-detail.component");
var question_type_master_update_component_1 = require("./question-type-master-update.component");
var QuestionTypeMasterResolve = /** @class */ (function () {
    function QuestionTypeMasterResolve(service, router) {
        this.service = service;
        this.router = router;
    }
    QuestionTypeMasterResolve.prototype.resolve = function (route) {
        var _this = this;
        var id = route.params['id'];
        if (id) {
            return this.service.find(id).pipe(operators_1.flatMap(function (questionTypeMaster) {
                if (questionTypeMaster.body) {
                    return rxjs_1.of(questionTypeMaster.body);
                }
                else {
                    _this.router.navigate(['404']);
                    return rxjs_1.EMPTY;
                }
            }));
        }
        return rxjs_1.of(new question_type_master_model_1.QuestionTypeMaster());
    };
    QuestionTypeMasterResolve = __decorate([
        core_1.Injectable({ providedIn: 'root' })
    ], QuestionTypeMasterResolve);
    return QuestionTypeMasterResolve;
}());
exports.QuestionTypeMasterResolve = QuestionTypeMasterResolve;
exports.questionTypeMasterRoute = [
    {
        path: '',
        component: question_type_category_master_component_1.QuestionTypeCategoryMasterComponent,
        data: {
            authorities: [authority_constants_1.Authority.USER],
            defaultSort: 'id,asc',
            pageTitle: 'QuestionTypeMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
    {
        path: ':id/view',
        component: question_type_master_detail_component_1.QuestionTypeMasterDetailComponent,
        resolve: {
            questionTypeMaster: QuestionTypeMasterResolve,
        },
        data: {
            authorities: [authority_constants_1.Authority.USER],
            pageTitle: 'QuestionTypeMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
    {
        path: 'new',
        component: question_type_master_update_component_1.QuestionTypeMasterUpdateComponent,
        resolve: {
            questionTypeMaster: QuestionTypeMasterResolve,
        },
        data: {
            authorities: [authority_constants_1.Authority.USER],
            pageTitle: 'QuestionTypeMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
    {
        path: ':id/edit',
        component: question_type_master_update_component_1.QuestionTypeMasterUpdateComponent,
        resolve: {
            questionTypeMaster: QuestionTypeMasterResolve,
        },
        data: {
            authorities: [authority_constants_1.Authority.USER],
            pageTitle: 'QuestionTypeMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
];
//# sourceMappingURL=question-type-master.route.js.map