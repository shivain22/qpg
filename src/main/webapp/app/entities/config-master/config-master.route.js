"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
exports.__esModule = true;
exports.configMasterRoute = exports.ConfigMasterResolve = void 0;
var core_1 = require("@angular/core");
var rxjs_1 = require("rxjs");
var operators_1 = require("rxjs/operators");
var authority_constants_1 = require("app/shared/constants/authority.constants");
var user_route_access_service_1 = require("app/core/auth/user-route-access-service");
var config_master_model_1 = require("app/shared/model/config-master.model");
var config_master_component_1 = require("./config-master.component");
var config_master_detail_component_1 = require("./config-master-detail.component");
var config_master_update_component_1 = require("./config-master-update.component");
var ConfigMasterResolve = /** @class */ (function () {
    function ConfigMasterResolve(service, router) {
        this.service = service;
        this.router = router;
    }
    ConfigMasterResolve.prototype.resolve = function (route) {
        var _this = this;
        var id = route.params['id'];
        if (id) {
            return this.service.find(id).pipe(operators_1.flatMap(function (configMaster) {
                if (configMaster.body) {
                    return rxjs_1.of(configMaster.body);
                }
                else {
                    _this.router.navigate(['404']);
                    return rxjs_1.EMPTY;
                }
            }));
        }
        return rxjs_1.of(new config_master_model_1.ConfigMaster());
    };
    ConfigMasterResolve = __decorate([
        core_1.Injectable({ providedIn: 'root' })
    ], ConfigMasterResolve);
    return ConfigMasterResolve;
}());
exports.ConfigMasterResolve = ConfigMasterResolve;
exports.configMasterRoute = [
    {
        path: '',
        component: config_master_component_1.ConfigMasterComponent,
        data: {
            authorities: [authority_constants_1.Authority.USER],
            defaultSort: 'id,asc',
            pageTitle: 'ConfigMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
    {
        path: ':id/view',
        component: config_master_detail_component_1.ConfigMasterDetailComponent,
        resolve: {
            configMaster: ConfigMasterResolve,
        },
        data: {
            authorities: [authority_constants_1.Authority.USER],
            pageTitle: 'ConfigMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
    {
        path: 'new',
        component: config_master_update_component_1.ConfigMasterUpdateComponent,
        resolve: {
            configMaster: ConfigMasterResolve,
        },
        data: {
            authorities: [authority_constants_1.Authority.USER],
            pageTitle: 'ConfigMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
    {
        path: ':id/edit',
        component: config_master_update_component_1.ConfigMasterUpdateComponent,
        resolve: {
            configMaster: ConfigMasterResolve,
        },
        data: {
            authorities: [authority_constants_1.Authority.USER],
            pageTitle: 'ConfigMasters',
        },
        canActivate: [user_route_access_service_1.UserRouteAccessService],
    },
];
//# sourceMappingURL=config-master.route.js.map