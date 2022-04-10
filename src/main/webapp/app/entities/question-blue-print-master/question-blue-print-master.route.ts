import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionBluePrintMaster, QuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';
import { QuestionBluePrintMasterService } from './question-blue-print-master.service';
import { QuestionBluePrintMasterComponent } from './question-blue-print-master.component';
import { QuestionBluePrintMasterDetailComponent } from './question-blue-print-master-detail.component';
import { QuestionBluePrintMasterUpdateComponent } from './question-blue-print-master-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionBluePrintMasterResolve implements Resolve<IQuestionBluePrintMaster> {
  constructor(private service: QuestionBluePrintMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionBluePrintMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionBluePrintMaster: HttpResponse<QuestionBluePrintMaster>) => {
          if (questionBluePrintMaster.body) {
            return of(questionBluePrintMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionBluePrintMaster());
  }
}

export const questionBluePrintMasterRoute: Routes = [
  {
    path: '',
    component: QuestionBluePrintMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'QuestionBluePrintMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionBluePrintMasterDetailComponent,
    resolve: {
      questionBluePrintMaster: QuestionBluePrintMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionBluePrintMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionBluePrintMasterUpdateComponent,
    resolve: {
      questionBluePrintMaster: QuestionBluePrintMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionBluePrintMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionBluePrintMasterUpdateComponent,
    resolve: {
      questionBluePrintMaster: QuestionBluePrintMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionBluePrintMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
