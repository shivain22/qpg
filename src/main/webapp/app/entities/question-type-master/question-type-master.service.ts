import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionTypeMaster } from 'app/shared/model/question-type-master.model';

type EntityResponseType = HttpResponse<IQuestionTypeMaster>;
type EntityArrayResponseType = HttpResponse<IQuestionTypeMaster[]>;

@Injectable({ providedIn: 'root' })
export class QuestionTypeMasterService {
  public resourceUrl = SERVER_API_URL + 'api/question-type-masters';

  constructor(protected http: HttpClient) {}

  create(questionTypeMaster: IQuestionTypeMaster): Observable<EntityResponseType> {
    return this.http.post<IQuestionTypeMaster>(this.resourceUrl, questionTypeMaster, { observe: 'response' });
  }

  update(questionTypeMaster: IQuestionTypeMaster): Observable<EntityResponseType> {
    return this.http.put<IQuestionTypeMaster>(this.resourceUrl, questionTypeMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionTypeMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionTypeMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
