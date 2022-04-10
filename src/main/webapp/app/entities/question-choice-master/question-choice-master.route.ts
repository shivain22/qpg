import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionChoiceMaster, QuestionChoiceMaster } from 'app/shared/model/question-choice-master.model';
import { QuestionChoiceMasterService } from './question-type-master.service';
import { QuestionChoiceMasterComponent } from './question-choice-master.component';

@Injectable({ providedIn: 'root' })
export class QuestionChoiceMasterResolve implements Resolve<IQuestionChoiceMaster> {
  constructor(private service: QuestionChoiceMasterService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionChoiceMaster> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionTypeMaster: HttpResponse<QuestionChoiceMaster>) => {
          if (questionTypeMaster.body) {
            return of(questionTypeMaster.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionChoiceMaster());
  }
}

export const questionChoiceMasterRoute: Routes = [
  {
    path: '',
    component: QuestionChoiceMasterComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'QuestionChoiceMasters',
    },
    canActivate: [UserRouteAccessService],
  },
];
