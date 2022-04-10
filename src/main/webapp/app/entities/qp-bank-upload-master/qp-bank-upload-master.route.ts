import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQpBankUploadMaster, QpBankUploadMaster } from 'app/shared/model/qp-bank-upload-master.model';
import { QpBankUploadMasterService } from './qp-bank-upload-master.service';
import { QpBankUploadMasterComponent } from './qp-bank-upload-master.component';
import { QpBankUploadMasterDetailComponent } from './qp-bank-upload-master-detail.component';
import { QpBankUploadMasterUpdateComponent } from './qp-bank-upload-master-update.component';

@Injectable({ providedIn: 'root' })
export class QpBankUploadMasterResolve implements Resolve<IQpBankUploadMaster> {
  constructor(private service: QpBankUploadMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQpBankUploadMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((qpBankUploadMaster: HttpResponse<QpBankUploadMaster>) => {
          if (qpBankUploadMaster.body) {
            return of(qpBankUploadMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QpBankUploadMaster());
  }
}

export const qpBankUploadMasterRoute: Routes = [
  {
    path: '',
    component: QpBankUploadMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'QpBankUploadMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QpBankUploadMasterDetailComponent,
    resolve: {
      qpBankUploadMaster: QpBankUploadMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QpBankUploadMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QpBankUploadMasterUpdateComponent,
    resolve: {
      qpBankUploadMaster: QpBankUploadMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QpBankUploadMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QpBankUploadMasterUpdateComponent,
    resolve: {
      qpBankUploadMaster: QpBankUploadMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QpBankUploadMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
