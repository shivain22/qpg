import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionMaster } from 'app/shared/model/question-master.model';

type EntityResponseType = HttpResponse<IQuestionMaster>;
type EntityArrayResponseType = HttpResponse<IQuestionMaster[]>;

@Injectable({ providedIn: 'root' })
export class QuestionMasterService {
  public resourceUrl = SERVER_API_URL + 'api/question-masters';

  constructor(protected http: HttpClient) {}

  create(questionMaster: IQuestionMaster): Observable<EntityResponseType> {
    return this.http.post<IQuestionMaster>(this.resourceUrl, questionMaster, { observe: 'response' });
  }

  upload(file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    const req = new HttpRequest('POST', this.resourceUrl, formData, {
      reportProgress: true,
      responseType: 'json',
    });
    return this.http.request(req);
  }

  update(questionMaster: IQuestionMaster): Observable<EntityResponseType> {
    return this.http.put<IQuestionMaster>(this.resourceUrl, questionMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
