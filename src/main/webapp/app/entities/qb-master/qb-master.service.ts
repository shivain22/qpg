import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQbMaster } from 'app/shared/model/qb-master.model';

type EntityResponseType = HttpResponse<IQbMaster>;
type EntityArrayResponseType = HttpResponse<IQbMaster[]>;

@Injectable({ providedIn: 'root' })
export class QbMasterService {
  public resourceUrl = SERVER_API_URL + 'api/qb-masters';

  constructor(protected http: HttpClient) {}

  create(qbMaster: IQbMaster): Observable<EntityResponseType> {
    return this.http.post<IQbMaster>(this.resourceUrl, qbMaster, { observe: 'response' });
  }

  update(qbMaster: IQbMaster): Observable<EntityResponseType> {
    return this.http.put<IQbMaster>(this.resourceUrl, qbMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQbMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQbMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
