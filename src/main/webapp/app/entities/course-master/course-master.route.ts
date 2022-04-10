import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICourseMaster, CourseMaster } from 'app/shared/model/course-master.model';
import { CourseMasterService } from './course-master.service';
import { CourseMasterComponent } from './course-master.component';
import { CourseMasterDetailComponent } from './course-master-detail.component';
import { CourseMasterUpdateComponent } from './course-master-update.component';

@Injectable({ providedIn: 'root' })
export class CourseMasterResolve implements Resolve<ICourseMaster> {
  constructor(private service: CourseMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICourseMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((courseMaster: HttpResponse<CourseMaster>) => {
          if (courseMaster.body) {
            return of(courseMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CourseMaster());
  }
}

export const courseMasterRoute: Routes = [
  {
    path: '',
    component: CourseMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'CourseMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CourseMasterDetailComponent,
    resolve: {
      courseMaster: CourseMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CourseMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CourseMasterUpdateComponent,
    resolve: {
      courseMaster: CourseMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CourseMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CourseMasterUpdateComponent,
    resolve: {
      courseMaster: CourseMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CourseMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
