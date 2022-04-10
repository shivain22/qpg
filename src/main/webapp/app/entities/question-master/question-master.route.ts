import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionMaster, QuestionMaster } from 'app/shared/model/question-master.model';
import { QuestionMasterService } from './question-master.service';
import { QuestionMasterComponent } from './question-master.component';
import { QuestionMasterDetailComponent } from './question-master-detail.component';
import { QuestionMasterUpdateComponent } from './question-master-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionMasterResolve implements Resolve<IQuestionMaster> {
  constructor(private service: QuestionMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionMaster: HttpResponse<QuestionMaster>) => {
          if (questionMaster.body) {
            return of(questionMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionMaster());
  }
}

export const questionMasterRoute: Routes = [
  {
    path: '',
    component: QuestionMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'QuestionMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionMasterDetailComponent,
    resolve: {
      questionMaster: QuestionMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionMasterUpdateComponent,
    resolve: {
      questionMaster: QuestionMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionMasterUpdateComponent,
    resolve: {
      questionMaster: QuestionMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
