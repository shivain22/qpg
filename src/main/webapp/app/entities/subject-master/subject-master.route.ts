import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubjectMaster, SubjectMaster } from 'app/shared/model/subject-master.model';
import { SubjectMasterService } from './subject-master.service';
import { SubjectMasterComponent } from './subject-master.component';
import { SubjectMasterDetailComponent } from './subject-master-detail.component';
import { SubjectMasterUpdateComponent } from './subject-master-update.component';

@Injectable({ providedIn: 'root' })
export class SubjectMasterResolve implements Resolve<ISubjectMaster> {
  constructor(private service: SubjectMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubjectMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subjectMaster: HttpResponse<SubjectMaster>) => {
          if (subjectMaster.body) {
            return of(subjectMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubjectMaster());
  }
}

export const subjectMasterRoute: Routes = [
  {
    path: '',
    component: SubjectMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubjectMasterDetailComponent,
    resolve: {
      subjectMaster: SubjectMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubjectMasterUpdateComponent,
    resolve: {
      subjectMaster: SubjectMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubjectMasterUpdateComponent,
    resolve: {
      subjectMaster: SubjectMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubjectMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
