import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnswerMaster } from 'app/shared/model/answer-master.model';

type EntityResponseType = HttpResponse<IAnswerMaster>;
type EntityArrayResponseType = HttpResponse<IAnswerMaster[]>;

@Injectable({ providedIn: 'root' })
export class AnswerMasterService {
  public resourceUrl = SERVER_API_URL + 'api/answer-masters';

  constructor(protected http: HttpClient) {}

  create(answerMaster: IAnswerMaster): Observable<EntityResponseType> {
    return this.http.post<IAnswerMaster>(this.resourceUrl, answerMaster, { observe: 'response' });
  }

  update(answerMaster: IAnswerMaster): Observable<EntityResponseType> {
    return this.http.put<IAnswerMaster>(this.resourceUrl, answerMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnswerMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnswerMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
