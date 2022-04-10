import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubjectMaster } from 'app/shared/model/subject-master.model';

type EntityResponseType = HttpResponse<ISubjectMaster>;
type EntityArrayResponseType = HttpResponse<ISubjectMaster[]>;

@Injectable({ providedIn: 'root' })
export class SubjectMasterService {
  public resourceUrl = SERVER_API_URL + 'api/subject-masters';

  constructor(protected http: HttpClient) {}

  create(subjectMaster: ISubjectMaster): Observable<EntityResponseType> {
    return this.http.post<ISubjectMaster>(this.resourceUrl, subjectMaster, { observe: 'response' });
  }

  update(subjectMaster: ISubjectMaster): Observable<EntityResponseType> {
    return this.http.put<ISubjectMaster>(this.resourceUrl, subjectMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubjectMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubjectMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
