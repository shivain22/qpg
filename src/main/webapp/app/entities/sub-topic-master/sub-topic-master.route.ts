import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubTopicMaster, SubTopicMaster } from 'app/shared/model/sub-topic-master.model';
import { SubTopicMasterService } from './sub-topic-master.service';
import { SubTopicMasterComponent } from './sub-topic-master.component';
import { SubTopicMasterDetailComponent } from './sub-topic-master-detail.component';
import { SubTopicMasterUpdateComponent } from './sub-topic-master-update.component';

@Injectable({ providedIn: 'root' })
export class SubTopicMasterResolve implements Resolve<ISubTopicMaster> {
  constructor(private service: SubTopicMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubTopicMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subTopicMaster: HttpResponse<SubTopicMaster>) => {
          if (subTopicMaster.body) {
            return of(subTopicMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubTopicMaster());
  }
}

export const subTopicMasterRoute: Routes = [
  {
    path: '',
    component: SubTopicMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'SubTopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubTopicMasterDetailComponent,
    resolve: {
      subTopicMaster: SubTopicMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubTopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubTopicMasterUpdateComponent,
    resolve: {
      subTopicMaster: SubTopicMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubTopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubTopicMasterUpdateComponent,
    resolve: {
      subTopicMaster: SubTopicMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'SubTopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
