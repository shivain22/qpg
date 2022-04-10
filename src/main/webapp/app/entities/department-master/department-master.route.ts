import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDepartmentMaster, DepartmentMaster } from 'app/shared/model/department-master.model';
import { DepartmentMasterService } from './department-master.service';
import { DepartmentMasterComponent } from './department-master.component';
import { DepartmentMasterDetailComponent } from './department-master-detail.component';
import { DepartmentMasterUpdateComponent } from './department-master-update.component';

@Injectable({ providedIn: 'root' })
export class DepartmentMasterResolve implements Resolve<IDepartmentMaster> {
  constructor(private service: DepartmentMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDepartmentMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((departmentMaster: HttpResponse<DepartmentMaster>) => {
          if (departmentMaster.body) {
            return of(departmentMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DepartmentMaster());
  }
}

export const departmentMasterRoute: Routes = [
  {
    path: '',
    component: DepartmentMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DepartmentMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DepartmentMasterDetailComponent,
    resolve: {
      departmentMaster: DepartmentMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DepartmentMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DepartmentMasterUpdateComponent,
    resolve: {
      departmentMaster: DepartmentMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DepartmentMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DepartmentMasterUpdateComponent,
    resolve: {
      departmentMaster: DepartmentMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DepartmentMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
