import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITopicMaster, TopicMaster } from 'app/shared/model/topic-master.model';
import { TopicMasterService } from './topic-master.service';
import { TopicMasterComponent } from './topic-master.component';
import { TopicMasterDetailComponent } from './topic-master-detail.component';
import { TopicMasterUpdateComponent } from './topic-master-update.component';

@Injectable({ providedIn: 'root' })
export class TopicMasterResolve implements Resolve<ITopicMaster> {
  constructor(private service: TopicMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITopicMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((topicMaster: HttpResponse<TopicMaster>) => {
          if (topicMaster.body) {
            return of(topicMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TopicMaster());
  }
}

export const topicMasterRoute: Routes = [
  {
    path: '',
    component: TopicMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'TopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TopicMasterDetailComponent,
    resolve: {
      topicMaster: TopicMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TopicMasterUpdateComponent,
    resolve: {
      topicMaster: TopicMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TopicMasterUpdateComponent,
    resolve: {
      topicMaster: TopicMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'TopicMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
