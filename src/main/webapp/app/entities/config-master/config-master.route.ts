import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IConfigMaster, ConfigMaster } from 'app/shared/model/config-master.model';
import { ConfigMasterService } from './config-master.service';
import { ConfigMasterComponent } from './config-master.component';
import { ConfigMasterDetailComponent } from './config-master-detail.component';
import { ConfigMasterUpdateComponent } from './config-master-update.component';

@Injectable({ providedIn: 'root' })
export class ConfigMasterResolve implements Resolve<IConfigMaster> {
  constructor(private service: ConfigMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConfigMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((configMaster: HttpResponse<ConfigMaster>) => {
          if (configMaster.body) {
            return of(configMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConfigMaster());
  }
}

export const configMasterRoute: Routes = [
  {
    path: '',
    component: ConfigMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ConfigMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConfigMasterDetailComponent,
    resolve: {
      configMaster: ConfigMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConfigMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConfigMasterUpdateComponent,
    resolve: {
      configMaster: ConfigMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConfigMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConfigMasterUpdateComponent,
    resolve: {
      configMaster: ConfigMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ConfigMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
