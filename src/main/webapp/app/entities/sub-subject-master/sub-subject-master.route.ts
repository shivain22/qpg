import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubSubjectMaster, SubSubjectMaster } from 'app/shared/model/sub-subject-master.model';
import { SubSubjectMasterService } from './sub-subject-master.service';
import { SubSubjectMasterComponent } from './sub-subject-master.component';
import { SubSubjectMasterDetailComponent } from './sub-subject-master-detail.component';
import { SubSubjectMasterUpdateComponent } from './sub-subject-master-update.component';

@Injectable({ providedIn: 'root' })
export class SubSubjectMasterResolve implements Resolve<ISubSubjectMaster> {
  constructor(private service: SubSubjectMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubSubjectMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subSubjectMaster: HttpResponse<SubSubjectMaster>) => {
          if (subSubjectMaster.body) {
            return of(subSubjectMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubSubjectMaster());
  }
}

export const subSubjectMasterRoute: Routes = [
  {
    path: '',
    component: SubSubjectMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SubSubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubSubjectMasterDetailComponent,
    resolve: {
      subSubjectMaster: SubSubjectMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubSubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubSubjectMasterUpdateComponent,
    resolve: {
      subSubjectMaster: SubSubjectMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubSubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubSubjectMasterUpdateComponent,
    resolve: {
      subSubjectMaster: SubSubjectMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubSubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
