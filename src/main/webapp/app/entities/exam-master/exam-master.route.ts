import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExamMaster, ExamMaster } from 'app/shared/model/exam-master.model';
import { ExamMasterService } from './exam-master.service';
import { ExamMasterComponent } from './exam-master.component';
import { ExamMasterDetailComponent } from './exam-master-detail.component';
import { ExamMasterUpdateComponent } from './exam-master-update.component';
import {ExamMasterCreateQuestionPaperDetailComponent} from "./exam-master-create-question-paper-detail.component";

@Injectable({ providedIn: 'root' })
export class ExamMasterResolve implements Resolve<IExamMaster> {
  constructor(private service: ExamMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExamMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((examMaster: HttpResponse<ExamMaster>) => {
          if (examMaster.body) {
            return of(examMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExamMaster());
  }
}

export const examMasterRoute: Routes = [
  {
    path: '',
    component: ExamMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ExamMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExamMasterDetailComponent,
    resolve: {
      examMaster: ExamMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/generateQuestionPaper',
    component: ExamMasterCreateQuestionPaperDetailComponent,
    resolve: {
      examMaster: ExamMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExamMasterUpdateComponent,
    resolve: {
      examMaster: ExamMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExamMasterUpdateComponent,
    resolve: {
      examMaster: ExamMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ExamMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
