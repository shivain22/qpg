import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionBankMaster } from 'app/shared/model/question-bank-master.model';

type EntityResponseType = HttpResponse<IQuestionBankMaster>;
type EntityArrayResponseType = HttpResponse<IQuestionBankMaster[]>;

@Injectable({ providedIn: 'root' })
export class QuestionBankMasterService {
  public resourceUrl = SERVER_API_URL + 'api/question-bank-masters';

  constructor(protected http: HttpClient) {}

  create(questionBankMaster: IQuestionBankMaster): Observable<EntityResponseType> {
    return this.http.post<IQuestionBankMaster>(this.resourceUrl, questionBankMaster, { observe: 'response' });
  }

  update(questionBankMaster: IQuestionBankMaster): Observable<EntityResponseType> {
    return this.http.put<IQuestionBankMaster>(this.resourceUrl, questionBankMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionBankMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionBankMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
