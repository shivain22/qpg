import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionBluePrintMaster } from 'app/shared/model/question-blue-print-master.model';

type EntityResponseType = HttpResponse<IQuestionBluePrintMaster>;
type EntityArrayResponseType = HttpResponse<IQuestionBluePrintMaster[]>;

@Injectable({ providedIn: 'root' })
export class QuestionBluePrintMasterService {
  public resourceUrl = SERVER_API_URL + 'api/question-blue-print-masters';

  constructor(protected http: HttpClient) {}

  create(questionBluePrintMaster: IQuestionBluePrintMaster): Observable<EntityResponseType> {
    return this.http.post<IQuestionBluePrintMaster>(this.resourceUrl, questionBluePrintMaster, { observe: 'response' });
  }

  update(questionBluePrintMaster: IQuestionBluePrintMaster): Observable<EntityResponseType> {
    return this.http.put<IQuestionBluePrintMaster>(this.resourceUrl, questionBluePrintMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionBluePrintMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionBluePrintMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
