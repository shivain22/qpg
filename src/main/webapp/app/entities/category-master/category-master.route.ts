import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoryMaster, CategoryMaster } from 'app/shared/model/category-master.model';
import { CategoryMasterService } from './category-master.service';
import { CategoryMasterComponent } from './category-master.component';
import { CategoryMasterDetailComponent } from './category-master-detail.component';
import { CategoryMasterUpdateComponent } from './category-master-update.component';

@Injectable({ providedIn: 'root' })
export class CategoryMasterResolve implements Resolve<ICategoryMaster> {
  constructor(private service: CategoryMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoryMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoryMaster: HttpResponse<CategoryMaster>) => {
          if (categoryMaster.body) {
            return of(categoryMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoryMaster());
  }
}

export const categoryMasterRoute: Routes = [
  {
    path: '',
    component: CategoryMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'CategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoryMasterDetailComponent,
    resolve: {
      categoryMaster: CategoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoryMasterUpdateComponent,
    resolve: {
      categoryMaster: CategoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoryMasterUpdateComponent,
    resolve: {
      categoryMaster: CategoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
