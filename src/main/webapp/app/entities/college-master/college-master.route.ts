import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICollegeMaster, CollegeMaster } from 'app/shared/model/college-master.model';
import { CollegeMasterService } from './college-master.service';
import { CollegeMasterComponent } from './college-master.component';
import { CollegeMasterDetailComponent } from './college-master-detail.component';
import { CollegeMasterUpdateComponent } from './college-master-update.component';

@Injectable({ providedIn: 'root' })
export class CollegeMasterResolve implements Resolve<ICollegeMaster> {
  constructor(private service: CollegeMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICollegeMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((collegeMaster: HttpResponse<CollegeMaster>) => {
          if (collegeMaster.body) {
            return of(collegeMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CollegeMaster());
  }
}

export const collegeMasterRoute: Routes = [
  {
    path: '',
    component: CollegeMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'CollegeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CollegeMasterDetailComponent,
    resolve: {
      collegeMaster: CollegeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CollegeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CollegeMasterUpdateComponent,
    resolve: {
      collegeMaster: CollegeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CollegeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CollegeMasterUpdateComponent,
    resolve: {
      collegeMaster: CollegeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CollegeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
