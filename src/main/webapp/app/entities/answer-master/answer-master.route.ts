import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnswerMaster, AnswerMaster } from 'app/shared/model/answer-master.model';
import { AnswerMasterService } from './answer-master.service';
import { AnswerMasterComponent } from './answer-master.component';
import { AnswerMasterDetailComponent } from './answer-master-detail.component';
import { AnswerMasterUpdateComponent } from './answer-master-update.component';

@Injectable({ providedIn: 'root' })
export class AnswerMasterResolve implements Resolve<IAnswerMaster> {
  constructor(private service: AnswerMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnswerMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((answerMaster: HttpResponse<AnswerMaster>) => {
          if (answerMaster.body) {
            return of(answerMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnswerMaster());
  }
}

export const answerMasterRoute: Routes = [
  {
    path: '',
    component: AnswerMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AnswerMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnswerMasterDetailComponent,
    resolve: {
      answerMaster: AnswerMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnswerMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnswerMasterUpdateComponent,
    resolve: {
      answerMaster: AnswerMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnswerMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnswerMasterUpdateComponent,
    resolve: {
      answerMaster: AnswerMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AnswerMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
