import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionTypeMaster, QuestionTypeMaster } from 'app/shared/model/question-type-master.model';
import { QuestionTypeCategoryMasterService } from './question-type-category-master.service';
import { QuestionTypeCategoryMasterComponent } from './question-type-category-master.component';
import { QuestionTypeCategoryMasterDetailComponent } from './question-type-category-master-detail.component';
import { QuestionTypeCategoryMasterUpdateComponent } from './question-type-category-master-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionTypeMasterResolve implements Resolve<IQuestionTypeMaster> {
  constructor(private service: QuestionTypeCategoryMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionTypeMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionTypeMaster: HttpResponse<QuestionTypeMaster>) => {
          if (questionTypeMaster.body) {
            return of(questionTypeMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionTypeMaster());
  }
}

export const questionTypeCategoryMasterRoute: Routes = [
  {
    path: '',
    component: QuestionTypeCategoryMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'QuestionTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionTypeCategoryMasterDetailComponent,
    resolve: {
      questionTypeMaster: QuestionTypeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionTypeCategoryMasterUpdateComponent,
    resolve: {
      questionTypeMaster: QuestionTypeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionTypeCategoryMasterUpdateComponent,
    resolve: {
      questionTypeMaster: QuestionTypeMasterResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'QuestionTypeMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
