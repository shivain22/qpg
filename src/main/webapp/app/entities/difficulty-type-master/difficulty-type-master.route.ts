import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDifficultyTypeMaster, DifficultyTypeMaster } from 'app/shared/model/difficulty-type-master.model';
import { DifficultyTypeMasterService } from './difficulty-type-master.service';
import { DifficultyTypeMasterComponent } from './difficulty-type-master.component';
import { DifficultyTypeMasterDetailComponent } from './difficulty-type-master-detail.component';
import { DifficultyTypeMasterUpdateComponent } from './difficulty-type-master-update.component';

@Injectable({ providedIn: 'root' })
export class DifficultyTypeMasterResolve implements Resolve<IDifficultyTypeMaster> {
  constructor(private service: DifficultyTypeMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDifficultyTypeMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((difficultyTypeMaster: HttpResponse<DifficultyTypeMaster>) => {
          if (difficultyTypeMaster.body) {
            return of(difficultyTypeMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DifficultyTypeMaster());
  }
}

export const difficultyTypeMasterRoute: Routes = [
  {
    path: '',
    component: DifficultyTypeMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DifficultyTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DifficultyTypeMasterDetailComponent,
    resolve: {
      difficultyTypeMaster: DifficultyTypeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DifficultyTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DifficultyTypeMasterUpdateComponent,
    resolve: {
      difficultyTypeMaster: DifficultyTypeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DifficultyTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DifficultyTypeMasterUpdateComponent,
    resolve: {
      difficultyTypeMaster: DifficultyTypeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DifficultyTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
