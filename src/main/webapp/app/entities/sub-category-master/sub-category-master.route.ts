import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubCategoryMaster, SubCategoryMaster } from 'app/shared/model/sub-category-master.model';
import { SubCategoryMasterService } from './sub-category-master.service';
import { SubCategoryMasterComponent } from './sub-category-master.component';
import { SubCategoryMasterDetailComponent } from './sub-category-master-detail.component';
import { SubCategoryMasterUpdateComponent } from './sub-category-master-update.component';

@Injectable({ providedIn: 'root' })
export class SubCategoryMasterResolve implements Resolve<ISubCategoryMaster> {
  constructor(private service: SubCategoryMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubCategoryMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subCategoryMaster: HttpResponse<SubCategoryMaster>) => {
          if (subCategoryMaster.body) {
            return of(subCategoryMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubCategoryMaster());
  }
}

export const subCategoryMasterRoute: Routes = [
  {
    path: '',
    component: SubCategoryMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SubCategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubCategoryMasterDetailComponent,
    resolve: {
      subCategoryMaster: SubCategoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubCategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubCategoryMasterUpdateComponent,
    resolve: {
      subCategoryMaster: SubCategoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubCategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubCategoryMasterUpdateComponent,
    resolve: {
      subCategoryMaster: SubCategoryMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubCategoryMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
