import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQbMaster, QbMaster } from 'app/shared/model/qb-master.model';
import { QbMasterService } from './qb-master.service';
import { QbMasterComponent } from './qb-master.component';
import { QbMasterDetailComponent } from './qb-master-detail.component';
import { QbMasterUpdateComponent } from './qb-master-update.component';

@Injectable({ providedIn: 'root' })
export class QbMasterResolve implements Resolve<IQbMaster> {
  constructor(private service: QbMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQbMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((qbMaster: HttpResponse<QbMaster>) => {
          if (qbMaster.body) {
            return of(qbMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QbMaster());
  }
}

export const qbMasterRoute: Routes = [
  {
    path: '',
    component: QbMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'QbMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QbMasterDetailComponent,
    resolve: {
      qbMaster: QbMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QbMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QbMasterUpdateComponent,
    resolve: {
      qbMaster: QbMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QbMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QbMasterUpdateComponent,
    resolve: {
      qbMaster: QbMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QbMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
