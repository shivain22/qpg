import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubSubjectMaster } from 'app/shared/model/sub-subject-master.model';

type EntityResponseType = HttpResponse<ISubSubjectMaster>;
type EntityArrayResponseType = HttpResponse<ISubSubjectMaster[]>;

@Injectable({ providedIn: 'root' })
export class SubSubjectMasterService {
  public resourceUrl = SERVER_API_URL + 'api/sub-subject-masters';

  constructor(protected http: HttpClient) {}

  create(subSubjectMaster: ISubSubjectMaster): Observable<EntityResponseType> {
    return this.http.post<ISubSubjectMaster>(this.resourceUrl, subSubjectMaster, { observe: 'response' });
  }

  update(subSubjectMaster: ISubSubjectMaster): Observable<EntityResponseType> {
    return this.http.put<ISubSubjectMaster>(this.resourceUrl, subSubjectMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubSubjectMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubSubjectMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
