"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.questionTypeMasterRoute = exports.QuestionChoiceMasterResolve = void 0;
var core_1 = require("@angular/core");
var rxjs_1 = require("rxjs");
var operators_1 = require("rxjs/operators");
var authority_constants_1 = require("app/shared/constants/authority.constants");
var user_route_access_service_1 = require("app/core/auth/user-route-access-service");
var question_choice_master_model_1 = require("app/shared/model/question-choice-master.model");
var question_choice_master_component_1 = require("./question-choice-master.component");
/*import { QuestionChoiceMasterDetailComponent } from './question-type-master-detail.component';
import { QuestionChoiceMasterUpdateComponent } from './question-type-master-update.component';*/
var QuestionChoiceMasterResolve = /** @class */ (function () {
    function QuestionChoiceMasterResolve(service, router) {
        this.service = service;
        this.router = router;
    }
    QuestionChoiceMasterResolve.prototype.resolve = function (route) {
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
        return rxjs_1.of(new question_choice_master_model_1.QuestionChoiceMaster());
    };
    QuestionChoiceMasterResolve = __decorate([
        core_1.Injectable({ providedIn: 'root' })
    ], QuestionChoiceMasterResolve);
    return QuestionChoiceMasterResolve;
}());
exports.QuestionChoiceMasterResolve = QuestionChoiceMasterResolve;
exports.questionTypeMasterRoute = [
    {
        path: '',
        component: question_choice_master_component_1.QuestionChoiceMasterComponent,
        data: {
            authorities: [authority_constants_1.Authority.USER],
            defaultSort: 'id,asc',
            pageTitle: 'QuestionChoiceMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    }
];
//# sourceMappingURL=question-choice-master.route.js.map