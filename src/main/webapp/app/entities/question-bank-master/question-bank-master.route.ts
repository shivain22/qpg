import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionBankMaster, QuestionBankMaster } from 'app/shared/model/question-bank-master.model';
import { QuestionBankMasterService } from './question-bank-master.service';
import { QuestionBankMasterComponent } from './question-bank-master.component';
import { QuestionBankMasterDetailComponent } from './question-bank-master-detail.component';
import { QuestionBankMasterUpdateComponent } from './question-bank-master-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionBankMasterResolve implements Resolve<IQuestionBankMaster> {
  constructor(private service: QuestionBankMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionBankMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionBankMaster: HttpResponse<QuestionBankMaster>) => {
          if (questionBankMaster.body) {
            return of(questionBankMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionBankMaster());
  }
}

export const questionBankMasterRoute: Routes = [
  {
    path: '',
    component: QuestionBankMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'QuestionBankMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionBankMasterDetailComponent,
    resolve: {
      questionBankMaster: QuestionBankMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionBankMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionBankMasterUpdateComponent,
    resolve: {
      questionBankMaster: QuestionBankMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionBankMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionBankMasterUpdateComponent,
    resolve: {
      questionBankMaster: QuestionBankMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionBankMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
