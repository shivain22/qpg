import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubTopicMaster } from 'app/shared/model/sub-topic-master.model';

type EntityResponseType = HttpResponse<ISubTopicMaster>;
type EntityArrayResponseType = HttpResponse<ISubTopicMaster[]>;

@Injectable({ providedIn: 'root' })
export class SubTopicMasterService {
  public resourceUrl = SERVER_API_URL + 'api/sub-topic-masters';

  constructor(protected http: HttpClient) {}

  create(subTopicMaster: ISubTopicMaster): Observable<EntityResponseType> {
    return this.http.post<ISubTopicMaster>(this.resourceUrl, subTopicMaster, { observe: 'response' });
  }

  update(subTopicMaster: ISubTopicMaster): Observable<EntityResponseType> {
    return this.http.put<ISubTopicMaster>(this.resourceUrl, subTopicMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubTopicMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubTopicMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
